package com.Test.Tivibu.controller;

import com.Test.Tivibu.dto.TesterDto;
import com.Test.Tivibu.model.users.TesterRequest;
import com.Test.Tivibu.service.TesterService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/tester")
public class TesterController {

    private final TesterService testerService;

    public TesterController(TesterService testerService) {
        this.testerService = testerService;
    }

    @PostMapping("/createTesterRequest")
    public ResponseEntity<TesterRequest> createLaborantRequest(@RequestBody TesterDto testerDto) {
        return ResponseEntity.ok(testerService.createNewTesterRequest(testerDto));
    }
}
