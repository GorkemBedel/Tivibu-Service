package com.Test.Tivibu.service;

import com.Test.Tivibu.dto.TesterDto;
import com.Test.Tivibu.model.users.Admin;
import com.Test.Tivibu.model.users.Tester;
import com.Test.Tivibu.repository.AdminRepository;
import com.Test.Tivibu.repository.TesterRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final TesterRepository testerRepository;
    private final TesterService testerService;

    public AdminService(AdminRepository adminRepository, TesterRepository testerRepository, TesterService testerService) {
        this.adminRepository = adminRepository;
        this.testerRepository = testerRepository;
        this.testerService = testerService;
    }

    public Tester createNewTester(TesterDto testerDto){
        Tester newTester = testerService.createNewTester(testerDto);

        return testerRepository.save(newTester);
    }

    public Optional<Admin> getByAdminName(String username) {

        return adminRepository.findByUsername(username);
    }
}
