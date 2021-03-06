package com.saptarga.demojwtauthentication.service.impl;

import com.saptarga.demojwtauthentication.entity.AppUser;
import com.saptarga.demojwtauthentication.entity.User;
import com.saptarga.demojwtauthentication.repository.UserRepository;
import com.saptarga.demojwtauthentication.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService, IUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return AppUser.build(user);
    }

    @Override
    public void updateStatusLoggedOut(String username) {
        User user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        user.setStatusLogin(false);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsernameAndStatusLoginTrue(String username) {
        User user =  userRepository.findByUsernameAndStatusLoginTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return AppUser.build(user);
    }

    @Override
    public void updateStatusLogged(String username) {
        User user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        user.setStatusLogin(true);
        userRepository.save(user);
    }
}
