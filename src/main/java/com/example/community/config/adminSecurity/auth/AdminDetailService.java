package com.example.community.config.adminSecurity.auth;

import com.example.community.admin.user.entity.Admin;
import com.example.community.admin.user.repo.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminDetailService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByAdminId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));

        return new AdminDetail(admin);
    }
}
