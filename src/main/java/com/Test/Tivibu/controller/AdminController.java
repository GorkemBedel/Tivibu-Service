package com.Test.Tivibu.controller;


import com.Test.Tivibu.dto.TesterDto;
import com.Test.Tivibu.model.users.Tester;
import com.Test.Tivibu.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("createNewTester")
    public ResponseEntity<Tester> crateNewTester(@RequestBody TesterDto testerDto){
        return ResponseEntity.ok(adminService.createNewTester(testerDto));
    }


}
