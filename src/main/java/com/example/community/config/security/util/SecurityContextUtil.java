package com.example.community.config.security.util;

import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityContextUtil {

    private final AuthenticationManager authenticationManager;

    public Account currentAccount() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof AccountDetail) {
            return ((AccountDetail) principal).getAccount();
        }
        return null;
    }

    public void anonymousAuthentication() {
        List<SimpleGrantedAuthority> role_anonymous = List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        AnonymousAuthenticationToken anonymousAuthenticationToken = new AnonymousAuthenticationToken("2316", "anonymousUser",role_anonymous);
        Authentication authenticate = authenticationManager.authenticate(anonymousAuthenticationToken);

        SecurityContextHolder.clearContext();
        SecurityContextHolder.getContext().setAuthentication(authenticate);
    }

}
