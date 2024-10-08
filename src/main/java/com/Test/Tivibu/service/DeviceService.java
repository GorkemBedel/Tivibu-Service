package com.Test.Tivibu.service;

import com.Test.Tivibu.dto.DeviceDto;
import com.Test.Tivibu.model.device.Device;
import com.Test.Tivibu.repository.DeviceRepository;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device addDevice(DeviceDto device) {

        String deviceType = device.deviceType();
        String version = device.version();


        Device newDevice = Device.builder()
                .deviceType(deviceType)
                .version(version)
                .build();

        return deviceRepository.save(newDevice);
    }

}
