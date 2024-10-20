package com.Test.Tivibu.service;

import com.Test.Tivibu.dto.DeviceDto;
import com.Test.Tivibu.mapper.DeviceMapper;
import com.Test.Tivibu.model.Device;
import com.Test.Tivibu.repository.DeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;


    public DeviceService(DeviceRepository deviceRepository, DeviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
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

    public Device getDeviceById(Long deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException(deviceId + " numaralı cihaz bulunamadı." ));
    }

    public void deleteDevice(Long deviceId) {

        Device toBeDeletedDevice = getDeviceById(deviceId);

        deviceRepository.deleteById(deviceId);
    }

    public Device updateDevice(Long deviceId, DeviceDto deviceDto) {

        Device toBeUpdatedDevice = getDeviceById(deviceId);

        deviceMapper.updateDeviceFromDto(deviceDto, toBeUpdatedDevice);

        return deviceRepository.save(toBeUpdatedDevice);

    }

    public List<Device> getAllDevices() {

        return deviceRepository.findAll();
    }
}
