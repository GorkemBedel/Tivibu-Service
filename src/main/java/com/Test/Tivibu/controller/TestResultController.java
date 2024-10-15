package com.Test.Tivibu.controller;

import com.Test.Tivibu.dto.TestResultDto;
import com.Test.Tivibu.model.TestResult;
import com.Test.Tivibu.service.TestResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/testResult")
public class TestResultController {

    private final TestResultService testResultService;

    public TestResultController(TestResultService testResultService) {
        this.testResultService = testResultService;
    }

    @PostMapping("addTestResult")
    public ResponseEntity<TestResult> addTestResult(@RequestBody TestResultDto testResultDto) {
        return ResponseEntity.ok(testResultService.addTestResult(testResultDto));
    }

    @GetMapping("getAllTestResults")
    public ResponseEntity<List<TestResult>> getAllTestResults() {
        return ResponseEntity.ok(testResultService.getAllTestResults());
    }

    @DeleteMapping("deleteTestResult/{testResultId}")
    public ResponseEntity<String> deleteTestResult(@PathVariable Long testResultId) {
        testResultService.deleteTestResult(testResultId);
        return ResponseEntity.ok(testResultId + " numaralı test sonucu silindi");
    }
}
