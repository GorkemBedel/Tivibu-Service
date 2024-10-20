package com.Test.Tivibu.repository;

import com.Test.Tivibu.model.SubTestResult;
import com.Test.Tivibu.model.Test;
import com.Test.Tivibu.model.TestResult;
import com.Test.Tivibu.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubTestResultRepository extends JpaRepository<SubTestResult, Long> {

}
