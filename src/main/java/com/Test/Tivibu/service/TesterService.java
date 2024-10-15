package com.Test.Tivibu.service;

import com.Test.Tivibu.dto.TesterDto;
import com.Test.Tivibu.exception.IdNotUniqueException;
import com.Test.Tivibu.model.Role;
import com.Test.Tivibu.model.users.Tester;
import com.Test.Tivibu.repository.TesterRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.PrimitiveIterator;

@Service
public class TesterService {

    private final TesterRepository testerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public TesterService(TesterRepository testerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.testerRepository = testerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Tester getTesterById(Long testerId) {
        return testerRepository.findById(testerId)
                .orElseThrow(() -> new RuntimeException(testerId + " numaralı testci bulunamadı." ));
    }

    public Optional<Tester> getTesterByName(String  name) {
        return testerRepository.findByUsername(name);

    }

    public Tester createNewTester(TesterDto testerDto){

        if(testerRepository.existsById(testerDto.testerId())){
            throw new IdNotUniqueException(testerDto.testerId() + "'ye sahip testci zaten bulunmakta.");
        }

        return   Tester.builder()
                .tester_id(testerDto.testerId())
                .name(testerDto.testerName())
                .username(testerDto.username())
                .password(bCryptPasswordEncoder.encode(testerDto.password()))
                .role(Role.ROLE_TESTER)
                .authorities(new HashSet<>(List.of(Role.ROLE_TESTER)))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .build();
    }
}
