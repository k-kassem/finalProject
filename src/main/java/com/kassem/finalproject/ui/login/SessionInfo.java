package com.kassem.finalproject.ui.login;

import org.springframework.context.annotation.Scope;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.kassem.finalproject.config.MyUserDetails;

@Component
@Scope("session")
public class SessionInfo {
    private MyUserDetails user;

    @Nullable
    public MyUserDetails getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null && principal instanceof MyUserDetails) {
            user = (MyUserDetails) principal;
        }
        return user;
    }
}