package com.saptarga.demojwtauthentication.dto.request;

import com.saptarga.demojwtauthentication.statval.ERole;

import java.util.Set;

public class RequestRegisterUserDto {

    private String username;
    private String email;
    private Set<ERole> role;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ERole> getRole() {
        return role;
    }

    public void setRole(Set<ERole> role) {
        this.role = role;
    }
}
