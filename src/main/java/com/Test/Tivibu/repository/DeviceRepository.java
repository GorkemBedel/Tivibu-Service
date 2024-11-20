package com.Test.Tivibu.repository;

import com.Test.Tivibu.model.Device;
import com.Test.Tivibu.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Query("SELECT DISTINCT d.deviceType FROM Device d")
    Set<String> findAllDeviceTypes();

    Optional<Device> findBydeviceType(String deviceType);

}
