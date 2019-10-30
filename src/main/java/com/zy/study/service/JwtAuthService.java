package com.zy.study.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtAuthService {

    UserDetails login(String username, String password);

    String username();
}
