package com.saptarga.demojwtauthentication.endpoint;

import com.saptarga.demojwtauthentication.dto.RequestLoginDto;
import com.saptarga.demojwtauthentication.dto.RequestRegisterUserDto;
import com.saptarga.demojwtauthentication.dto.ResponseLoginDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/auth",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public interface IAuthEndpoint {

    @PostMapping(value = "/register-user")
    ResponseEntity<String> registerUser(@RequestBody RequestRegisterUserDto requestRegisterUserDto);

    @PostMapping("/login")
    ResponseEntity<ResponseLoginDto> loginUser(@RequestBody RequestLoginDto requestLoginDto);

}
