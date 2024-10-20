package com.Test.Tivibu.repository;

import com.Test.Tivibu.model.Test;
import com.Test.Tivibu.model.TestResult;
import com.Test.Tivibu.model.Device;
import com.Test.Tivibu.model.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {

    Optional<TestResult> findByDeviceAndTest(Device device, Test test);

//    @Query("SELECT testResult FROM TestResult testResult JOIN testResult.device device WHERE device.deviceType = :deviceType")
//    List<TestResult> findTestResultsByDeviceType(@Param("deviceType") String deviceType);

    @Query("SELECT testResult FROM TestResult testResult JOIN FETCH testResult.device device WHERE device.deviceType = :deviceType")
    List<TestResult> findTestResultsByDeviceType(@Param("deviceType") String deviceType);


//    Optional<List<TestResult>> findByDevice(String device);


}
