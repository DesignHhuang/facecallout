/*
package com.face.callout.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //允许所有用户访问"/"和"/home"
        http.csrf().disable()
                .authorizeRequests()
//                .antMatchers("/api/v1/user/*").permitAll()
                //其他地址的访问均需验证权限
                .anyRequest().permitAll();
//                .anyRequest().authenticated();
    }

}*/
