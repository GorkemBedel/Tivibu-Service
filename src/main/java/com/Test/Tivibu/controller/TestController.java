package com.Test.Tivibu.controller;

import com.Test.Tivibu.dto.DeviceDto;
import com.Test.Tivibu.dto.TestDto;
import com.Test.Tivibu.model.Test;
import com.Test.Tivibu.model.Device;
import com.Test.Tivibu.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("v1/test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("addTest")
    public ResponseEntity<Test> addTest(@RequestBody TestDto testDto) {

        return ResponseEntity.ok(testService.addTest(testDto));
    }

    @GetMapping("getAllTests")
    public ResponseEntity<List<Test>> getAllTests() {

        return ResponseEntity.ok(testService.getAllTests());
    }

    @GetMapping("getTestsByType/{type}")
    public ResponseEntity<List<Test>> getAllTests(@PathVariable String type) {

        return ResponseEntity.ok(testService.getTestsByType(type));
    }

    @GetMapping("getTestTypes")
    public ResponseEntity<Set<String>> getTestTypes() {
        return ResponseEntity.ok(testService.getTestTypes());
    }

    @GetMapping("getTest/{testId}")
    public ResponseEntity<Test> getAllTests(@PathVariable Long testId) {

        return ResponseEntity.ok(testService.getTestById(testId));
    }


    @DeleteMapping("deleteTest/{testId}")
    public ResponseEntity<String> deleteTest(@PathVariable Long testId) {
        testService.deleteDevice(testId);
        return ResponseEntity.ok(testId + " numaralı test silindi");
    }

    @PatchMapping("updateTest/{testId}")
    public ResponseEntity<Test> updateDevice(@PathVariable Long testId,
                                             @RequestBody TestDto testDto) {
        return ResponseEntity.ok(testService.updateDevice(testId, testDto));
    }
}
