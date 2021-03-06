package com.example.community.config.security;

import com.example.community.config.security.auth.AccountDetailService;
import com.example.community.config.security.hanlder.FailureHandlerCustom;
import com.example.community.config.security.hanlder.SuccessUrlHandlerCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AccountDetailService accountDetailService;
    private final PasswordEncoder passwordEncoder;

    private final SuccessUrlHandlerCustom successUrlHandlerCustom;
    private final FailureHandlerCustom failureHandlerCustom;
    private final String[] WHITE_LIST = {"/images/**", "/js/**", "/css/**", "/profile/**", "/favicon.ico","/notice/**"};

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountDetailService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                .regexMatchers(HttpMethod.GET, "/info/\\d+$", "/guestBook/\\d+$").permitAll()
                .regexMatchers(HttpMethod.POST, "/guestBook/\\d+$").authenticated()
                .antMatchers("/", "/sign", "/register/**", "/logout", "/login/**", "/image/upload", "/download/**", "/search/**", "/forgotPassword").permitAll()
                .antMatchers("/post/create").authenticated()
                .antMatchers("/post/**").permitAll()
                .antMatchers(WHITE_LIST).permitAll()
                .antMatchers("/notification/**").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("id")
                .passwordParameter("password")
                .successHandler(successUrlHandlerCustom)
                .failureHandler(failureHandlerCustom)
                .permitAll();

        http.csrf().ignoringAntMatchers("/image/upload", "/forgotPassword");

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
