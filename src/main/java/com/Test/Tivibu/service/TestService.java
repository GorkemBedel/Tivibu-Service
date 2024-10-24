package com.Test.Tivibu.service;

import com.Test.Tivibu.dto.TestDto;
import com.Test.Tivibu.exception.UsernameNotUniqueException;
import com.Test.Tivibu.mapper.TestMapper;
import com.Test.Tivibu.model.Test;
import com.Test.Tivibu.repository.TestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    private final TestRepository testRepository;
    private final TestMapper testMapper;

    public TestService(TestRepository testRepository, TestMapper testMapper) {
        this.testRepository = testRepository;
        this.testMapper = testMapper;
    }

    public Test addTest(TestDto testDto) {

        List<String> subTests = testDto.subTests().orElse(List.of());
        String testName = testDto.testName();
        String type = testDto.type();

        Test newTest = Test.builder()
                .testName(testName)
                .subTests(subTests)
                .type(type)
                .build();

        return testRepository.save(newTest);
    }

    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    public Test getTestById(Long testId) {
        return testRepository.findById(testId)
                .orElseThrow(() -> new UsernameNotUniqueException(testId + " numaralı test bulunamadı." ));
    }

    public void deleteDevice(Long testId) {

        Test toBeDeletedTest = getTestById(testId);

        testRepository.deleteById(testId);
    }

    public Test updateDevice(Long testId, TestDto testDto) {

        Test toBeUpdatedTest = getTestById(testId);

        testMapper.updateTestFromDto(testDto, toBeUpdatedTest);

        return testRepository.save(toBeUpdatedTest);
    }

    public List<Test> getTestsByType(String type) {

        return testRepository.findByType(type);
    }
}
