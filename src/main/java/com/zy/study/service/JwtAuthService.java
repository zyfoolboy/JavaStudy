package com.zy.study.service;

import com.zy.study.dao.model.User;
import com.zy.study.dto.RegisterParam;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtAuthService {

    UserDetails login(String username, String password);

    String username();

    User register(RegisterParam registerParam);
}
