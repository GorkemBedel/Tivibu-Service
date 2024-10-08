package com.Test.Tivibu.model;

import com.Test.Tivibu.model.device.Device;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test_result")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "v1_result_id", referencedColumnName = "id")
    private Result v1_result;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "v2_result_id", referencedColumnName = "id")
    private Result v2_result;






    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;  // Every "TestResult" object has to belong to a "Device"

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;     // Every "TestResult" object has to belong to a "Test"
}
