package com.sip.api.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public  class SessionUtils {

    public static String getLoggedUserUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
