package com.example.community.config.security.hanlder;

import com.example.community.admin.accountManage.entity.AccountBlock;
import com.example.community.admin.accountManage.repo.AccountBlockRepository;
import com.example.community.domain.account.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class FailureHandlerCustom  implements AuthenticationFailureHandler {

    private final AccountBlockRepository accountBlockRepository;
    private final AccountRepository accountRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errormsg;
        if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            errormsg = "BadCredentialsException";
        } else if (exception instanceof DisabledException) {
            errormsg = "DisabledException";
        } else if(exception instanceof LockedException) {
            String email = request.getParameter("id");
            AccountBlock accountBlock = accountRepository.findFirstByEmailEquals(email)
                    .flatMap(accountBlockRepository::findByBlockAccount)
                    .orElseThrow();

            String blockComment = accountBlock.getBlockComment();
            LocalDateTime blockUntilDate = accountBlock.getBlockUntilDate();
            response.sendRedirect("/login?error=LockedException&comment=" + blockComment + "&untilTime=" + blockUntilDate);
            return;
        } else {
            response.sendRedirect("/login");
            return;
        }
        response.sendRedirect("/login?error=" + errormsg);
    }
}
