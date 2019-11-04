package com.zy.study.service.impl;

import com.zy.study.config.JwtTokenUtil;
import com.zy.study.dao.mapper.UserMapper;
import com.zy.study.dao.model.User;
import com.zy.study.dto.RegisterParam;
import com.zy.study.service.JwtAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthServiceImpl implements JwtAuthService {

    @Autowired
    private JwtUserDetailServiceImpl jwtUserDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public String login(String username, String password) throws BadCredentialsException {
        UserDetails userDetails = null;
        try {
            userDetails = jwtUserDetailService.loadUserByUsername(username);
            if (userDetails == null) {
                throw new BadCredentialsException("user not found");
            }
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("password error");
            }
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("username not found");
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new BadCredentialsException("user not found");
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("password error");
        }

        String token = jwtTokenUtil.generateToken(userDetails);

        return token;
    }

    @Override
    public String username() {
        User user = userMapper.selectByPrimaryKey(1);
        return user.getUsername();
    }

    @Override
    public User register(RegisterParam registerParam) {
        User user = new User();
        BeanUtils.copyProperties(registerParam, user);

        String encoderPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoderPassword);
        userMapper.insert(user);

        return user;
    }
}
