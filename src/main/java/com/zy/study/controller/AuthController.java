package com.zy.study.controller;

import com.zy.study.dao.model.User;
import com.zy.study.dto.LoginParam;
import com.zy.study.dto.RegisterParam;
import com.zy.study.service.JwtAuthService;
import com.zy.study.utils.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private JwtAuthService jwtAuthService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult login(@RequestBody LoginParam loginParam) {
        String token = null;
        try {
            token = jwtAuthService.login(loginParam.getUsername(), loginParam.getPassword());
        } catch (BadCredentialsException e) {
            return ApiResult.failed();
        }

        if (token == null) {
            return ApiResult.failed();
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);

        return ApiResult.success(tokenMap);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult register(@RequestBody RegisterParam registerParam) {
        User user = jwtAuthService.register(registerParam);

        Optional<User> userOpt = Optional.ofNullable(user);

        return userOpt.map(ApiResult::success).orElse(ApiResult.failed());
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

}
