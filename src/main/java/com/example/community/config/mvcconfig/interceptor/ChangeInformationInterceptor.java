package com.example.community.config.mvcconfig.interceptor;

import com.example.community.config.security.auth.AccountDetail;
import com.example.community.domain.account.entity.Account;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Transactional(readOnly = true)
@Component
@RequiredArgsConstructor
@Slf4j
public class ChangeInformationInterceptor implements HandlerInterceptor {

    private final AccountRepository accountRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (!request.getMethod().equalsIgnoreCase("post"))
            return;

        Account account = ((AccountDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getAccount();
        accountRepository.findById(account.getId()).ifPresent(account1 -> {
            log.info(account1.toString());
            HttpSession session = request.getSession(false);

            session.setAttribute("profile",account1.getProfileImg());
            session.setAttribute("nickname", account1.getNickname());
        });
    }
}
