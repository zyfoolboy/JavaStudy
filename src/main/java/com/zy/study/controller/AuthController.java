package com.zy.study.controller;

import com.zy.study.config.JwtTokenUtil;
import com.zy.study.dto.LoginParam;
import com.zy.study.dto.RegisterParam;
import com.zy.study.service.JwtAuthService;
import com.zy.study.service.impl.JwtUserDetailServiceImpl;
import com.zy.study.utils.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailServiceImpl userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtAuthService jwtAuthService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult login(@RequestBody LoginParam loginParam) throws Exception {
        Map<String, String> tokenMap = new HashMap<>();
        authLogin(loginParam.getUsername(), loginParam.getPassword());
        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(loginParam.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);
        tokenMap.put("token", token);

        return ApiResult.success(tokenMap);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult register(@RequestBody RegisterParam registerParam) throws Exception {
        Map<String, String> resultMap = new HashMap<>();

        return ApiResult.success(resultMap);
    }

    @RequestMapping(value = "/first", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult first() {
        Map<String, String> tokenMap = new HashMap<>();

        tokenMap.put("first", "123");
        tokenMap.put("name", jwtAuthService.username());
        LOGGER.info("info hello");
        LOGGER.error("error hello");
        return ApiResult.success(tokenMap);
    }

    private void authLogin(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
