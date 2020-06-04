package com.face.callout.controller;

import java.util.HashMap;
import java.util.Map;

import com.face.callout.common.restmodel.RestResult;
import com.face.callout.common.restmodel.RestResultBuilder;
import com.face.callout.config.JwtTokenProvider;
import com.face.callout.entity.User;
import com.face.callout.repository.UserRepository;
import com.face.callout.service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    final
    private AuthenticationManager authenticationManager;

    final
    private JwtTokenProvider jwtTokenProvider;

    final
    private UserRepository users;

    private final CustomUserDetailsService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository users, CustomUserDetailsService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.users = users;
        this.userService = userService;
    }

    @PostMapping("/login")
    @SuppressWarnings("unchecked")
    public RestResult login(@RequestBody @Valid AuthBody data) {
        try {
            String username = data.getMobile();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            String token = jwtTokenProvider.createToken(username, this.users.findByMobile(username).getRoleList());
            Map<Object, Object> model = new HashMap<>();
            model.put("mobile", username);
            model.put("token", token);
            model.put("user", this.users.findByMobile(username));
            return RestResultBuilder.builder().success(model).build();
        } catch (AuthenticationException e) {
            String mobile = data.getMobile();
            Map<Object, Object> model = new HashMap<>();
            model.put("mobile", mobile);
            model.put("message", "输入的密码有误");
            return RestResultBuilder.builder().failure(model).build();
        }
    }

    @PostMapping("/register")
    @SuppressWarnings("unchecked")
    public RestResult register(@RequestBody @Valid User user) {
        User userExists = userService.findUserByEmailOrMobile(user.getEmail(), user.getMobile());
        Map<Object, Object> model = new HashMap<>();
        if (userExists != null) {
            model.put("success", 0);
            model.put("message", "注册的邮箱或手机号已经被使用");
            model.put("user", user);
            return RestResultBuilder.builder().failure(model).build();
        } else {
            user.setAvatar("group1/M00/00/00/wKi_gF7XXMSAK16MAAHpXdrl2D0900.jpg");
            userService.saveUser(user);
            model.put("success", 1);
            model.put("message", "用户注册成功");
            model.put("user", user);
            return RestResultBuilder.builder().success(model).build();
        }
    }
}