package com.Test.Tivibu.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class WhoAuthenticated {

    public static String whoIsAuthenticated(){
        //Checking if the laborant is trying to update his/her OWN report
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}