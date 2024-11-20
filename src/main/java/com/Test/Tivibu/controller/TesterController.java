package com.Test.Tivibu.controller;

import com.Test.Tivibu.dto.TesterDto;
import com.Test.Tivibu.dto.TesterEmailDto;
import com.Test.Tivibu.model.users.TesterRequest;
import com.Test.Tivibu.service.TesterService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/tester")
public class TesterController {

    private final TesterService testerService;

    public TesterController(TesterService testerService) {
        this.testerService = testerService;
    }

//    @PostMapping("/createTesterRequest")
//    public ResponseEntity<TesterRequest> createLaborantRequest(@RequestBody TesterDto testerDto) {
//        return ResponseEntity.ok(testerService.createNewTesterRequest(testerDto));
//    }

    @PostMapping("/register")
    public ResponseEntity<String> registerTester(@RequestBody TesterEmailDto testerDto) {
        try {
            testerService.saveTester(testerDto);
            return ResponseEntity.ok("User registered successfully! Please check your email to verify your account.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmUser(@RequestParam("token") String token) {
        try {
            testerService.confirmTester(token);
            return ResponseEntity.ok("Account verified successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getTesterId")
    public ResponseEntity<Long> getTesterId() {
        return ResponseEntity.ok(testerService.getTesterId());
    }

    @DeleteMapping("deleteTester/{testerId}")
    public ResponseEntity<String> deleteTest(@PathVariable Long testerId) {
        testerService.deleteTester(testerId);
        return ResponseEntity.ok(testerId + " numaralı test silindi");
    }


}
