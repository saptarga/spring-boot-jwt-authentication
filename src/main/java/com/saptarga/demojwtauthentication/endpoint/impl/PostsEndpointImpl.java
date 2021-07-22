package com.saptarga.demojwtauthentication.endpoint.impl;

import com.saptarga.demojwtauthentication.endpoint.IPostsEndpoint;
import com.saptarga.demojwtauthentication.entity.AppUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostsEndpointImpl implements IPostsEndpoint {

    @Override
    public ResponseEntity<String> getDataPosts(Authentication authentication) {

        // this is for get data principal user
        AppUser userDetails = (AppUser) authentication.getPrincipal();

        return ResponseEntity.ok(String.format("This is public endpoint for get list posts for user %s", userDetails.getUsername()));
    }

}
