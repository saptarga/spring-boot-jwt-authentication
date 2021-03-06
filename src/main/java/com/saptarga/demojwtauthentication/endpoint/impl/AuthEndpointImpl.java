package com.saptarga.demojwtauthentication.endpoint.impl;

import com.saptarga.demojwtauthentication.dto.request.RequestLoginDto;
import com.saptarga.demojwtauthentication.dto.request.RequestRefreshToken;
import com.saptarga.demojwtauthentication.dto.request.RequestRegisterUserDto;
import com.saptarga.demojwtauthentication.dto.response.ResponseLoginDto;
import com.saptarga.demojwtauthentication.dto.response.ResponseRefreshToken;
import com.saptarga.demojwtauthentication.endpoint.IAuthEndpoint;
import com.saptarga.demojwtauthentication.entity.AppUser;
import com.saptarga.demojwtauthentication.entity.RefreshToken;
import com.saptarga.demojwtauthentication.entity.Role;
import com.saptarga.demojwtauthentication.entity.User;
import com.saptarga.demojwtauthentication.exception.TokenRefreshException;
import com.saptarga.demojwtauthentication.repository.RoleRepository;
import com.saptarga.demojwtauthentication.repository.UserRepository;
import com.saptarga.demojwtauthentication.security.jwt.JwtUtils;
import com.saptarga.demojwtauthentication.service.IAuthService;
import com.saptarga.demojwtauthentication.service.IRefreshTokenService;
import com.saptarga.demojwtauthentication.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class AuthEndpointImpl implements IAuthEndpoint {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    IRefreshTokenService refreshTokenService;

    @Autowired
    IAuthService iAuthService;

    @Autowired
    IUserService userService;

    @Override
    public ResponseEntity<String> registerUser(RequestRegisterUserDto request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        Set<Role> roles = new HashSet<>();
        request.getRole().forEach(role -> {
            Role userRole = roleRepository.findByName(role)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        });
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok("register user");
    }

    @Override
    public ResponseEntity<ResponseLoginDto> loginUser(RequestLoginDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        AppUser userDetails = (AppUser) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        userService.updateStatusLogged(request.getUsername());

        return ResponseEntity.ok(new ResponseLoginDto(jwt,
                refreshToken.getToken(),
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @Override
    public ResponseEntity<ResponseRefreshToken> refreshtoken(RequestRefreshToken request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new ResponseRefreshToken(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @Override
    public ResponseEntity<String> logoutUSer(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        AppUser userDetails = (AppUser) authentication.getPrincipal();
        if (authentication != null){
            userService.updateStatusLoggedOut(userDetails.getUsername());
            iAuthService.logout(httpServletRequest, httpServletResponse, authentication);
            return ResponseEntity.ok("Logout Success");
        }
        return ResponseEntity.ok("Logout Error");
    }

}
