package com.example.community.config.security.hanlder;

import com.example.community.config.security.auth.AccountDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class SuccessUrlHandlerCustom implements AuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();
        HttpSession session = request.getSession();
        sessionSetProfileAndNickname(accountDetail,session);

        if(savedRequest != null) {
            response.sendRedirect(savedRequest.getRedirectUrl());
        } else {
            response.sendRedirect("/");
        }
    }

    private void sessionSetProfileAndNickname(AccountDetail accountDetail, HttpSession session) {
        String profileImg = accountDetail.getAccount().getProfileImg();

        if (StringUtils.hasText(profileImg)) {
            session.setAttribute("profile",profileImg);
        } else {
            session.setAttribute("profile","person.png");
        }
        session.setAttribute("nickname", accountDetail.getAccount().getNickname());

    }
}
