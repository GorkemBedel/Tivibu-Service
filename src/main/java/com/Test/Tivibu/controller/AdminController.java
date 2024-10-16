package com.Test.Tivibu.controller;


import com.Test.Tivibu.dto.TesterDto;
import com.Test.Tivibu.model.Test;
import com.Test.Tivibu.model.users.Tester;
import com.Test.Tivibu.model.users.TesterRequest;
import com.Test.Tivibu.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/approveTesterRequest/{id}")
    public ResponseEntity<Tester> approveLaborantRequest(@PathVariable("id") Long id) {
        return ResponseEntity.ok(adminService.approveTesterRequest(id));
    }

    @GetMapping("/testerRequests")
    public ResponseEntity<List<TesterRequest>> getAllTesterRequests() {
        return ResponseEntity.ok(adminService.getAllTesterRequests());
    }

    @GetMapping("/getAllTesters")
    public ResponseEntity<List<Tester>> getAllTesters() {
        return ResponseEntity.ok(adminService.getAllTesters());
    }


}
