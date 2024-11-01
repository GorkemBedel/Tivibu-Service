package com.Test.Tivibu.service;

import com.Test.Tivibu.dto.ResultDto;
import com.Test.Tivibu.dto.SubTestResultDto;
import com.Test.Tivibu.dto.TestResultDto;
//import com.Test.Tivibu.model.Test;
import com.Test.Tivibu.dto.TestResultResponse;
import com.Test.Tivibu.exception.FalseTestWithoutCommentException;
import com.Test.Tivibu.exception.TestHasNoSubTestsException;
import com.Test.Tivibu.exception.UsernameNotUniqueException;
import com.Test.Tivibu.model.Result;
import com.Test.Tivibu.model.SubTestResult;
import com.Test.Tivibu.model.Test;
import com.Test.Tivibu.model.TestResult;
import com.Test.Tivibu.model.Device;
import com.Test.Tivibu.model.users.Tester;
import com.Test.Tivibu.repository.DeviceRepository;
import com.Test.Tivibu.repository.SubTestResultRepository;
import com.Test.Tivibu.repository.TestRepository;
import com.Test.Tivibu.repository.TestResultRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestResultService {

    private final TestResultRepository testResultRepository;
    private final SubTestResultRepository subTestResultRepository;
    private final TestService testService;
    private final DeviceService deviceService;
    private final TesterService testerService;

    public TestResultService(TestResultRepository testResultRepository, SubTestResultRepository subTestResultRepository, TestService testService, DeviceService deviceService, TesterService testerService) {
        this.testResultRepository = testResultRepository;
        this.subTestResultRepository = subTestResultRepository;
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

        String tivibuVersion = testResult.tivibuVersion();





        // ******************************************  V1    R E S U L T      ******************************************
        ResultDto v1_result_dto = testResult.v1_result();

        if(!v1_result_dto.isOk()){ // false ise
            if(v1_result_dto.comment().trim().isEmpty()) {
                throw new FalseTestWithoutCommentException(" Eğer test başarısız ise yorum girmeniz zorunludur.");
            }
        }
        Result v1_result = Result.builder()
                .isOk(v1_result_dto.isOk())
                .comment(v1_result_dto.comment())
                .build();



        // ******************************************  V2    R E S U L T      ******************************************

        ResultDto v2_result_dto = testResult.v2_result();

        if(!v2_result_dto.isOk()){ // false ise
            if(v2_result_dto.comment().trim().isEmpty()) {
                throw new FalseTestWithoutCommentException(" Eğer test başarısız ise yorum girmeniz zorunludur.");
            }
        }

        Result v2_result = Result.builder()
                .isOk(v2_result_dto.isOk())
                .comment(v2_result_dto.comment())
                .build();


        // *************************** if that test result exists, then update it   ************************************
        Optional<TestResult> existingTestResultOptional = testResultRepository.findByDeviceAndTest(device, test);

        if(existingTestResultOptional.isPresent()){
            TestResult toBeUpdatedTestResult = existingTestResultOptional.get();

            Result toBeUpdatedV1_Result = toBeUpdatedTestResult.getV1_result();
            Result toBeUpdatedV2_Result = toBeUpdatedTestResult.getV2_result();

            toBeUpdatedV1_Result.setIsOk(v1_result.getIsOk());
            toBeUpdatedV2_Result.setIsOk(v2_result.getIsOk());

            toBeUpdatedV1_Result.setComment(v1_result.getComment());
            toBeUpdatedV2_Result.setComment(v2_result.getComment());

            // if both v1 and v2 results are true, then testOk is true
            toBeUpdatedTestResult.setTestOk(v1_result.getIsOk() && v2_result.getIsOk());
            toBeUpdatedTestResult.setTester(tester);
            toBeUpdatedTestResult.setSubTestsResults(null);
            return testResultRepository.save(toBeUpdatedTestResult);
        }


//        List<ResultDto> subTestResultsDto = testResult.subTestsResults().orElse(List.of());

//        List<Result> subTestsResults = subTestResultsDto.stream()
//                .map(subTestResult -> Result.builder()
//                        .isOk(subTestResult.isOk())
//                        .comment(subTestResult.comment())
//                        .build())
//                .collect(Collectors.toList());

        // *************************** if that test result does not exist, then create it   ***************************
        TestResult newTest = TestResult.builder()
                .tivibuVersion(tivibuVersion)
                .test(test)
                .subTestsResults(new ArrayList<>())
                .device(device)
                .tester(tester)
                .v1_result(v1_result)
                .v2_result(v2_result)
                .subTestsResults(null)
                .testResultDate(LocalDateTime.now())
                .build();
        newTest.setTestOk(v1_result.getIsOk() && v2_result.getIsOk());


        return testResultRepository.save(newTest);
    }

    public TestResult uploadPhotoToAnExistingTestResult(Long id, MultipartFile file){

        TestResult toBeUpdatedTestResult = getTestResultById(id);
        try {
            toBeUpdatedTestResult.setTestPhoto(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return testResultRepository.save(toBeUpdatedTestResult);

    }


    public TestResult addTestResultForTestWhichHasSubTests(TestResultDto testResult) {

        Long testId = testResult.testId();
        Test test = testService.getTestById(testId);

        if(test.getSubTests().isEmpty()){
            throw new TestHasNoSubTestsException("Bu testin alt testleri olmamalıdır.");
        }



        Long deviceId = testResult.deviceId();
        Device device = deviceService.getDeviceById(deviceId);

        Long testerId = testResult.testerId();
        Tester tester = testerService.getTesterById(testerId);

        String tivibuVersion = testResult.tivibuVersion();



        // *************************** if that test result exists, then update it   ************************************
        Optional<TestResult> existingTestResultOptional = testResultRepository.findByDeviceAndTest(device, test);

        if(existingTestResultOptional.isPresent()){
            TestResult toBeUpdatedTestResult = existingTestResultOptional.get();


            // getting updated sub test results
            updateSubTestResults(testResult, toBeUpdatedTestResult);

            toBeUpdatedTestResult.setTester(tester);
            return testResultRepository.save(toBeUpdatedTestResult);
        }



        // *************************** if that test result does not exist, then create it   ***************************
        TestResult newTest = TestResult.builder()
                .tivibuVersion(tivibuVersion)
                .test(test)
                .subTestsResults(new ArrayList<>())
                .device(device)
                .tester(tester)
                .v1_result(null)
                .v2_result(null)
                .testResultDate(LocalDateTime.now())
                .build();
        newTest.setTestOk(true);



        // ***************************        getting sub test results     ***************************
        List<SubTestResult> subTestsResults = getSubTestResults(testResult, newTest);
        newTest.setSubTestsResults(subTestsResults);


        return testResultRepository.save(newTest);
    }

    public List<SubTestResult> getSubTestResults(TestResultDto testResult, TestResult newTest) {
        return testResult.subTestsResults()
                .map(subTestResultsDto -> {

                    List<SubTestResult> subTestsResults = subTestResultsDto.stream()
                            .map(subTestResult -> SubTestResult.builder()
                                    .testResult(newTest)
                                    .v1_isOk(subTestResult.v1_isOk())
                                    .v1_comment(subTestResult.v1_comment())
                                    .v2_isOk(subTestResult.v2_isOk())
                                    .v2_comment(subTestResult.v2_comment())
                                    .build())
                            .collect(Collectors.toList());

                    int subTestNumber = 0;
                    int trueSubTestNumber = 0;

                    for (SubTestResult subTestResult : subTestsResults) {
                        if (subTestResult.getV1_isOk() && subTestResult.getV2_isOk()) {
                            subTestResult.setOk(true);
                            trueSubTestNumber++;
                        }else if(  (!subTestResult.getV1_isOk() && subTestResult.getV1_comment().trim().isEmpty() )
                                || (!subTestResult.getV2_isOk() && subTestResult.getV2_comment().trim().isEmpty() ) ){
                            throw new FalseTestWithoutCommentException(" Eğer test başarısız ise yorum girmeniz zorunludur.");
                        }
                        subTestNumber++;
                    }

                    newTest.setTestOk(subTestNumber == trueSubTestNumber);

                    return subTestsResults;
                })
                .orElse(Collections.emptyList()); // Eğer subTestResults() boşsa, boş bir liste döner
    }

    public void updateSubTestResults(TestResultDto testResult, TestResult existingTestResult) {

        //database den mevcut subTestResult listesini çekiyorum
        List<SubTestResult> existingSubTestResults = existingTestResult.getSubTestsResults();

        //dto dan gelen güncellenmiş subTestResults listesini alıyoruz
        List<SubTestResult> updatedSubTestsResults = getSubTestResults(testResult, existingTestResult);

        //güncel olan subTestResult listesini, elimizdeki subTestResult listesinin içine atıyoruz. böylece yeni subTestResult objeleri oluşturulmuyor

        for(int i = 0; i < existingSubTestResults.size(); i++){

            SubTestResult existingSubTestResult = existingSubTestResults.get(i);
            SubTestResult updatedSubTestResult = updatedSubTestsResults.get(i);


            existingSubTestResult.setTestResult(existingSubTestResult.getTestResult());
            existingSubTestResult.setV1_isOk(updatedSubTestResult.getV1_isOk());
            existingSubTestResult.setV1_comment(updatedSubTestResult.getV1_comment());
            existingSubTestResult.setV2_isOk(updatedSubTestResult.getV2_isOk());
            existingSubTestResult.setV2_comment(updatedSubTestResult.getV2_comment());

            for (SubTestResult subTestResult : existingSubTestResults) {
                subTestResult.setOk(subTestResult.getV1_isOk() && subTestResult.getV2_isOk());
            }
        }


        int subTestNumber = 0;
        int trueSubTestNumber = 0;

        for (SubTestResult subTestResult : updatedSubTestsResults) {
            if (subTestResult.getV1_isOk() && subTestResult.getV2_isOk()) {
                subTestResult.setOk(true);
                trueSubTestNumber++;
            }
            subTestNumber++;
        }
        existingTestResult.setTestOk(subTestNumber == trueSubTestNumber);




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

    public List<TestResult> getTestResultsByDeviceType(String deviceType){
        return testResultRepository.findTestResultsByDeviceType(deviceType);
    }



    public List<TestResult> getTestResultsByDeviceTypeDescending(String deviceType){
        return testResultRepository.findByDevice_DeviceTypeOrderByTestOkAsc(deviceType);
    }

    public List<TestResult> getTestResultsByDeviceTypeAndTivibuVersionDescending(String deviceType, String tivibuVersion){
        return testResultRepository.findByDevice_DeviceTypeAndTivibuVersionOrderByTestOkAsc(deviceType, tivibuVersion);
    }

    public Set<String> getAllTivibuVersions() {
        return testResultRepository.findAllTivibuVersions();

    }
}
