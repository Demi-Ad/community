package com.example.community.config.adminSecurity.util;

import com.example.community.admin.user.entity.Admin;
import com.example.community.config.adminSecurity.auth.AdminDetail;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AdminSecurityContextUtil {

    public Admin currentAdmin() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof AdminDetail) {
            return ((AdminDetail) principal).getAdmin();
        }
        return null;
    }
}
