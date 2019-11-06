package com.zy.study.service;

import com.zy.study.dao.model.User;
import com.zy.study.dto.RegisterParam;

import java.util.Optional;

public interface JwtAuthService {

    Optional<String> login(String username, String password);

    String username();

    User register(RegisterParam registerParam);
}
