package com.Test.Tivibu.service;

import com.Test.Tivibu.dto.TesterDto;
import com.Test.Tivibu.model.users.Admin;
import com.Test.Tivibu.model.users.Tester;
import com.Test.Tivibu.model.users.TesterRequest;
import com.Test.Tivibu.repository.AdminRepository;
import com.Test.Tivibu.repository.TesterRepository;
import com.Test.Tivibu.repository.TesterRequestRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final TesterRepository testerRepository;
    private final TesterService testerService;
    private final TesterRequestRepository testerRequestRepository;


    public AdminService(AdminRepository adminRepository, TesterRepository testerRepository, TesterService testerService, TesterRequestRepository testerRequestRepository) {
        this.adminRepository = adminRepository;
        this.testerRepository = testerRepository;
        this.testerService = testerService;
        this.testerRequestRepository = testerRequestRepository;
    }

    public Tester approveTesterRequest(Long id){

        //tester requesti database'den çek
        TesterRequest testerRequest = testerRequestRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(id + " numaralı test istegi bulunamadı." ));

        // tester requesti testera çevir
        Tester newTester = testerService.createNewTesterByTesterRequest(testerRequest);

        //tester requesti sil
        testerRequestRepository.deleteById(id);

        return testerRepository.save(newTester);

    }

    public Optional<Admin> getByAdminName(String username) {

        return adminRepository.findByUsername(username);
    }

    public List<TesterRequest> getAllTesterRequests() {

        return testerRequestRepository.findAll();
    }

    public List<Tester> getAllTesters() {

        return testerRepository.findAll();
    }
}
