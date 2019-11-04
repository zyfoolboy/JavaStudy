package com.zy.study.service;

import com.zy.study.dao.model.User;
import com.zy.study.dto.RegisterParam;
import org.springframework.security.authentication.BadCredentialsException;

public interface JwtAuthService {

    String login(String username, String password) throws BadCredentialsException;

    String username();

    User register(RegisterParam registerParam);
}
