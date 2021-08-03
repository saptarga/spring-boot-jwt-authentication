package com.saptarga.demojwtauthentication.endpoint;

import com.saptarga.demojwtauthentication.dto.request.RequestLoginDto;
import com.saptarga.demojwtauthentication.dto.request.RequestRefreshToken;
import com.saptarga.demojwtauthentication.dto.request.RequestRegisterUserDto;
import com.saptarga.demojwtauthentication.dto.response.ResponseLoginDto;
import com.saptarga.demojwtauthentication.dto.response.ResponseRefreshToken;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping(value = "/api/auth",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public interface IAuthEndpoint {

    @PostMapping(value = "/register-user")
    ResponseEntity<String> registerUser(@RequestBody RequestRegisterUserDto requestRegisterUserDto);

    @PostMapping("/login")
    ResponseEntity<ResponseLoginDto> loginUser(@RequestBody RequestLoginDto requestLoginDto);

    @PostMapping("/refresh-token")
    ResponseEntity<ResponseRefreshToken> refreshtoken(@RequestBody RequestRefreshToken request);

    @PostMapping("/logout")
    ResponseEntity<String> logoutUSer(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication);

}
