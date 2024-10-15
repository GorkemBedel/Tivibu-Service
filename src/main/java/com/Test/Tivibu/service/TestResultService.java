package com.Test.Tivibu.service;

import com.Test.Tivibu.dto.ResultDto;
import com.Test.Tivibu.dto.TestResultDto;
//import com.Test.Tivibu.model.Test;
import com.Test.Tivibu.model.Result;
import com.Test.Tivibu.model.Test;
import com.Test.Tivibu.model.TestResult;
import com.Test.Tivibu.model.device.Device;
import com.Test.Tivibu.model.users.Tester;
import com.Test.Tivibu.repository.DeviceRepository;
import com.Test.Tivibu.repository.TestRepository;
import com.Test.Tivibu.repository.TestResultRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestResultService {

    private final TestResultRepository testResultRepository;
    private final TestService testService;
    private final DeviceService deviceService;
    private final TesterService testerService;

    public TestResultService(TestResultRepository testResultRepository, TestService testService, DeviceService deviceService, TesterService testerService) {
        this.testResultRepository = testResultRepository;
        this.testService = testService;
        this.deviceService = deviceService;
        this.testerService = testerService;
    }

    public TestResult addTestResult(TestResultDto testResult) {

        Long testId = testResult.testId();
        Test test = testService.getTestById(testId);

        Long deviceId = testResult.deviceId();
        Device device = deviceService.getDeviceById(deviceId);

        Long testerId = testResult.testerId();
        Tester tester = testerService.getTesterById(testerId);

        Optional<TestResult> existingTestResultOptional = testResultRepository.findByDeviceAndTest(device, test);




        ResultDto v1_result_dto = testResult.v1_result();

        if(!v1_result_dto.isOk()){ // false ise
            if(v1_result_dto.comment().trim().isEmpty()) {
                throw new RuntimeException(" Eğer test başarısız ise yorum girmeniz zorunludur.");
            }
        }

        Result v1_result = Result.builder()
                .isOk(v1_result_dto.isOk())
                .comment(v1_result_dto.comment())
                .build();



        ResultDto v2_result_dto = testResult.v2_result();

        if(!v2_result_dto.isOk()){ // false ise
            if(v2_result_dto.comment().trim().isEmpty()) {
                throw new RuntimeException(" Eğer test başarısız ise yorum girmeniz zorunludur.");
            }
        }

        Result v2_result = Result.builder()
                .isOk(v2_result_dto.isOk())
                .comment(v2_result_dto.comment())
                .build();

        if(existingTestResultOptional.isPresent()){
            TestResult toBeUpdatedTestResult = existingTestResultOptional.get();

            toBeUpdatedTestResult.setV1_result(v1_result);
            toBeUpdatedTestResult.setV2_result(v2_result);
            toBeUpdatedTestResult.setTester(tester);
            return testResultRepository.save(toBeUpdatedTestResult);
        }


//        List<ResultDto> subTestResultsDto = testResult.subTestsResults().orElse(List.of());

//        List<Result> subTestsResults = subTestResultsDto.stream()
//                .map(subTestResult -> Result.builder()
//                        .isOk(subTestResult.isOk())
//                        .comment(subTestResult.comment())
//                        .build())
//                .collect(Collectors.toList());


        TestResult newTest = TestResult.builder()
                .test(test)
                .device(device)
                .tester(tester)
                .v1_result(v1_result)
                .v2_result(v2_result)
//                .subTestsResults(subTestResultsDto)
                .build();

        return testResultRepository.save(newTest);
    }


    public List<TestResult> getAllTestResults() {
        return testResultRepository.findAll();
    }

    public TestResult getTestResultById(Long testId) {
        return testResultRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException(testId + " numaralı test sonucu bulunamadı." ));
    }


    public void deleteTestResult(Long testResultId) {

        TestResult toBeDeletedTest = getTestResultById(testResultId);

        testResultRepository.deleteById(testResultId);
    }
}
