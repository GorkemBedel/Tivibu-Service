package com.Test.Tivibu.controller;

import com.Test.Tivibu.dto.DeviceDto;
import com.Test.Tivibu.model.Device;
import com.Test.Tivibu.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("v1/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("addDevice")
    public ResponseEntity<Device> addDevice(@RequestBody DeviceDto deviceDto) {
        return ResponseEntity.ok(deviceService.addDevice(deviceDto));
    }

    @GetMapping("getDevice/{deviceId}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long deviceId) {
        return ResponseEntity.ok(deviceService.getDeviceById(deviceId));
    }

    @GetMapping("getAllDevices")
    public ResponseEntity<List<Device>> getDevice() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @GetMapping("getAllDeviceTypes")
    public ResponseEntity<Set<String>> getDeviceTypes() {
        return ResponseEntity.ok(deviceService.getAllDeviceTypes());
    }

    @GetMapping("getDeviceIdByDeviceType")
    public ResponseEntity<Long> getDeviceIdByDeviceType(@PathVariable(name = "deviceType") String deviceType) {
        return ResponseEntity.ok(deviceService.getDeviceIdByDeviceType(deviceType));
    }

    @DeleteMapping("deleteDevice/{deviceId}")
    public ResponseEntity<String> deleteDevice(@PathVariable Long deviceId) {
        deviceService.deleteDevice(deviceId);
        return ResponseEntity.ok(deviceId + " numaralı cihaz silindi");
    }

    @PatchMapping("updateDevice/{deviceId}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long deviceId,
                                               @RequestBody DeviceDto deviceDto) {
        return ResponseEntity.ok(deviceService.updateDevice(deviceId, deviceDto));
    }

}
