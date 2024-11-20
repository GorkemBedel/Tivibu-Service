package com.Test.Tivibu.controller;

import com.Test.Tivibu.dto.UserInformationDto;
import com.Test.Tivibu.service.GetUserInformationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/getUserInformation")
public class GetUserInformationController {

    private final GetUserInformationService getUserInformationService;

    public GetUserInformationController(GetUserInformationService getUserInformationService) {
        this.getUserInformationService = getUserInformationService;
    }

    @GetMapping("getUserInformation")
    public ResponseEntity<UserInformationDto> getUserInformation() {
        return ResponseEntity.ok(getUserInformationService.getUserInformation());
    }
}
