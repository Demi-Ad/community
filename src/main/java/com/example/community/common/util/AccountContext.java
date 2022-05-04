package com.example.community.common.util;

import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.account.entity.Account;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AccountContext {

    public Account getAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof AccountDetail) {
            return ((AccountDetail) principal).getAccount();
        }
        return null;
    }
}
