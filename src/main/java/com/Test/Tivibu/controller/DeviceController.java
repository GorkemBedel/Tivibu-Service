package com.Test.Tivibu.controller;

import com.Test.Tivibu.dto.DeviceDto;
import com.Test.Tivibu.model.device.Device;
import com.Test.Tivibu.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/device")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("addDevice")
    public ResponseEntity<Device> addDevice(@RequestBody DeviceDto device) {
        return ResponseEntity.ok(deviceService.addDevice(device));

    }
}
