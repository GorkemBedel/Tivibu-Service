package com.Test.Tivibu.service;

import com.Test.Tivibu.dto.TesterDto;
import com.Test.Tivibu.exception.IdNotUniqueException;
import com.Test.Tivibu.exception.UsernameNotUniqueException;
import com.Test.Tivibu.model.Role;
import com.Test.Tivibu.model.users.Tester;
import com.Test.Tivibu.model.users.TesterRequest;
import com.Test.Tivibu.repository.TesterRepository;
import com.Test.Tivibu.repository.TesterRequestRepository;
import com.Test.Tivibu.rule.TesterValidator;
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
    private final TesterValidator testerValidator;
    private final TesterRequestRepository testerRequestRepository;

    public TesterService(TesterRepository testerRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TesterValidator testerValidator, TesterRequestRepository testerRequestRepository) {
        this.testerRepository = testerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.testerValidator = testerValidator;
        this.testerRequestRepository = testerRequestRepository;
    }

    public Tester getTesterById(Long testerId) {
        return testerRepository.findById(testerId)
                .orElseThrow(() -> new UsernameNotUniqueException(testerId + " numaralı testci bulunamadı." ));
    }

    public Optional<Tester> getTesterByName(String  name) {
        return testerRepository.findByUsername(name);

    }

    public TesterRequest createNewTesterRequest(TesterDto testerDto){

        testerValidator.validateTesterId(testerDto.testerId());

        if(testerRepository.existsById(testerDto.testerId())){
            throw new UsernameNotUniqueException("Bu sicil numarasıyla kayıtlı bir testci zaten bulunmakta!");
        }

        if(testerRequestRepository.existsById(testerDto.testerId())){
            throw new UsernameNotUniqueException("Bu sicil numarasıyla daha önce bir kayıt isteğinde bulunulmuş!");
        }

        if(testerRepository.existsByUsername(testerDto.username())){
            throw new UsernameNotUniqueException("Bu kullanıcı adı ile kayıtlı bir testci zaten bulunmakta!");
        }

        if(testerRequestRepository.existsByUsername(testerDto.username())){
            throw new UsernameNotUniqueException("Bu kullanıcı adı ile daha önce bir kayıt isteğinde bulunulmuş!");
        }

        TesterRequest testerRequest = TesterRequest.builder()
                .tester_id(testerDto.testerId())
                .name(testerDto.testerName())
                .username(testerDto.username())
                .password(bCryptPasswordEncoder.encode(testerDto.password()))
                .build();

        return testerRequestRepository.save(testerRequest);
    }

    public Tester createNewTesterByTesterRequest(TesterRequest testerRequest){


        //checking if id is 6 digit or not
        testerValidator.validateTesterId(testerRequest.getTester_id());

        if(testerRepository.existsById(testerRequest.getTester_id())){
            throw new IdNotUniqueException(testerRequest.getTester_id() + " sicil numaralı testci zaten bulunmakta.");
        }

        return   Tester.builder()
                .tester_id(testerRequest.getTester_id())
                .name(testerRequest.getName())
                .username(testerRequest.getUsername())
                .password(testerRequest.getPassword())
                .role(Role.ROLE_TESTER)
                .authorities(new HashSet<>(List.of(Role.ROLE_TESTER)))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .build();
    }
}
