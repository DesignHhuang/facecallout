package com.face.callout.controller;

import com.face.callout.entity.User;
import com.face.callout.exception.BusinessException;
import com.face.callout.exception.GlobalErrorCode;
import com.face.callout.repository.UserRepository;
import com.face.callout.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class BaseController {
    @Autowired
    CustomUserDetailsService authenticationService;

    @Autowired
    UserRepository userRepository;

    User getCurrentUser() {
        UserDetails userDetails = authenticationService.currentUser();
        if (userDetails == null) {
            throw new BusinessException(GlobalErrorCode.UNAUTHORIZED);
        }
        User user = userRepository.findUserByMobileAndIsdeletedFalse(userDetails.getUsername());
        if (user != null) {
            return user;
        } else {
            throw new BusinessException(GlobalErrorCode.NOT_FOUND);
        }
    }
}
