package com.Test.Tivibu.repository;

import com.Test.Tivibu.model.device.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, String> {
}