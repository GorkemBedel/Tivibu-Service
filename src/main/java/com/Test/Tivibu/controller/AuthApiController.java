package com.Test.Tivibu.controller;

import com.Test.Tivibu.dto.TesterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthApiController {
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody TesterDto testerDto) {
        // Kullanıcı kaydetme işlemleri burada yapılacak.
        return ResponseEntity.ok().build(); // Başarılı kayıt
    }


}