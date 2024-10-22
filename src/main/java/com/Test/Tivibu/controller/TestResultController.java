package com.Test.Tivibu.controller;

import com.Test.Tivibu.dto.TestResultDto;
import com.Test.Tivibu.model.TestResult;
import com.Test.Tivibu.service.TestResultService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("addTestResultForSubTests")
    public ResponseEntity<TestResult> addTestResultForSubTests(@RequestBody TestResultDto testResultDto) {
        return ResponseEntity.ok(testResultService.addTestResultForTestWhichHasSubTests(testResultDto));
    }

    @GetMapping("getAllTestResults")
    public ResponseEntity<List<TestResult>> getAllTestResults() {
        return ResponseEntity.ok(testResultService.getAllTestResults());
    }

    @GetMapping("getTestResult/{testResultId}")
    public ResponseEntity<TestResult> getTestResult(@PathVariable Long testResultId){
        return ResponseEntity.ok(testResultService.getTestResultById(testResultId));
    }

    @GetMapping("getTestResultsByDeviceType/{deviceType}")
    public ResponseEntity<List<TestResult>> getAllTestResults(@PathVariable String deviceType) {
        return ResponseEntity.ok(testResultService.getTestResultsByDeviceType(deviceType));
    }

    @PostMapping("addPhoto/{testResultId}")
    public ResponseEntity<TestResult> addPhoto(@PathVariable Long testResultId, @RequestParam("file") MultipartFile file){

        return ResponseEntity.ok(testResultService.uploadPhotoToAnExistingTestResult(testResultId, file));

    }

    @DeleteMapping("deleteTestResult/{testResultId}")
    public ResponseEntity<String> deleteTestResult(@PathVariable Long testResultId) {
        testResultService.deleteTestResult(testResultId);
        return ResponseEntity.ok(testResultId + " numaralı test sonucu silindi");
    }
}
