package com.Test.Tivibu.model;

import com.Test.Tivibu.dto.ResultDto;
import com.Test.Tivibu.model.device.Device;
import com.Test.Tivibu.model.users.Tester;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test_result")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "v1_result_id", referencedColumnName = "id")
    private Result v1_result;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "v2_result_id", referencedColumnName = "id")
    private Result v2_result;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tester_id", referencedColumnName = "tester_id", nullable = false)
    private Tester tester;

//    @OneToMany(mappedBy = "testResult", cascade = CascadeType.ALL)
//    private List<Result> subTestsResults;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;  // Every "TestResult" object has to belong to a "Device"





    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;     // Every "TestResult" object has to belong to a "Test"

}
