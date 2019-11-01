package com.zy.study.service.impl;

import com.zy.study.dao.mapper.UserMapper;
import com.zy.study.dto.RegisterParam;
import com.zy.study.service.JwtAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;

@Service
public class JwtAuthServiceImpl implements JwtAuthService {

//    @Autowired
//    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails login(String username, String password) {
        return new User("zyzyzy", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                new ArrayList<>());
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        if (passwordEncoder.matches(password, userDetails.getPassword())) {
//
//        }
//
//        return userDetails;
    }

    @Override
    public String username() {

        com.zy.study.dao.model.User user = userMapper.selectByPrimaryKey(1);
        return user.getUsername();
    }

    @Override
    public com.zy.study.dao.model.User register(RegisterParam registerParam) {
        com.zy.study.dao.model.User user = new com.zy.study.dao.model.User();
        BeanUtils.copyProperties(registerParam, user);

        String encoderPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoderPassword);
        userMapper.insert(user);

        return null;
    }
}
