package com.Test.Tivibu.service;

import com.Test.Tivibu.dto.UserInformationDto;
import com.Test.Tivibu.model.Role;
import com.Test.Tivibu.model.users.Admin;
import com.Test.Tivibu.model.users.Tester;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetUserInformationService {

    private final TesterService testerService;
    private final AdminService adminService;

    public GetUserInformationService(TesterService testerService, AdminService adminService) {
        this.testerService = testerService;
        this.adminService = adminService;
    }

    public UserInformationDto getUserInformation() {
        String currentUsername = WhoAuthenticated.whoIsAuthenticated();

        Optional<Tester> tester = testerService.getTesterByName(currentUsername);
        Optional<Admin> admin = adminService.getByAdminName(currentUsername);

        if (admin.isPresent()) {
            Admin adminPresent = admin.get();
            Long id = adminPresent.getAdmin_id();
            String name = adminPresent.getName();
            String userName = adminPresent.getUsername();
            String role = adminPresent.getRole().getValue();
            return new UserInformationDto(id, name, userName, role);

        } else if (tester.isPresent()) {
            Tester testerPresent = tester.get();
            Long id = testerPresent.getTester_id();
            String name = testerPresent.getName();
            String userName = testerPresent.getUsername();
            String role = testerPresent.getRole().getValue();
            return new UserInformationDto(id, name, userName, role);
        } else {
            return new UserInformationDto(0L, null, null, null);
        }

    }
}