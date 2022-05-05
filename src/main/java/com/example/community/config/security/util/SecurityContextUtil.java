package com.example.community.config.security.util;

import com.example.community.config.security.auth.AccountDetail;
import com.example.community.config.security.util.exception.SecurityContextNotFoundException;
import com.example.community.domain.account.entity.Account;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextUtil {

    public Account currentAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof AccountDetail) {
            return ((AccountDetail) principal).getAccount();
        }
        throw new SecurityContextNotFoundException();
    }

}
