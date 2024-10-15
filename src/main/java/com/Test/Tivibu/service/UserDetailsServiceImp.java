package com.Test.Tivibu.service;

import com.Test.Tivibu.model.users.Admin;
import com.Test.Tivibu.model.users.Tester;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final AdminService adminService;
    private final TesterService testerService;

    public UserDetailsServiceImp(AdminService adminService, TesterService testerService) {
        this.adminService = adminService;
        this.testerService = testerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Tester> tester = testerService.getTesterByName(username);
        Optional<Admin> admin = adminService.getByAdminName(username);

        if(admin.isPresent()){
            return admin.get();
        }else if(tester.isPresent()) {
            return tester.get();
        }
        else{
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
