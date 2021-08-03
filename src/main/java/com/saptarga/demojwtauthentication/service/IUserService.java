package com.saptarga.demojwtauthentication.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService {

    void updateStatusLoggedOut(String username);

    UserDetails loadUserByUsernameAndStatusLoginTrue(String username);

    void updateStatusLogged(String username);
}
