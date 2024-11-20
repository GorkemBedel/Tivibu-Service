package com.Test.Tivibu.service;

import com.Test.Tivibu.dto.TesterDto;
import com.Test.Tivibu.dto.TesterEmailDto;
import com.Test.Tivibu.exception.IdNotUniqueException;
import com.Test.Tivibu.exception.UsernameNotUniqueException;
import com.Test.Tivibu.model.Confirmation;
import com.Test.Tivibu.model.Role;
import com.Test.Tivibu.model.users.Tester;
import com.Test.Tivibu.model.users.TesterRequest;
import com.Test.Tivibu.repository.ConfirmationTokenRepository;
import com.Test.Tivibu.repository.TesterRepository;
import com.Test.Tivibu.repository.TesterRequestRepository;
import com.Test.Tivibu.rule.TesterValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;

    public TesterService(TesterRepository testerRepository,
                         BCryptPasswordEncoder bCryptPasswordEncoder,
                         TesterValidator testerValidator,
                         TesterRequestRepository testerRequestRepository,
                         ConfirmationTokenRepository confirmationTokenRepository,
                         EmailService emailService) {
        this.testerRepository = testerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.testerValidator = testerValidator;
        this.testerRequestRepository = testerRequestRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.emailService = emailService;
    }

    public void saveTester(TesterEmailDto testerDto) {
        if (testerRepository.existsByEmail(testerDto.email())) {
            throw new IllegalArgumentException("Bu email adresi ile kayıtlı bir testci zaten bulunmakta!");
        }

        Tester newTester = Tester.builder()
                .tester_id(testerDto.testerId())
                .name(testerDto.testerName())
                .email(testerDto.email())
                .username(testerDto.username())
                .password(bCryptPasswordEncoder.encode(testerDto.password()))
                .role(Role.ROLE_TESTER)
                .authorities(new HashSet<>(List.of(Role.ROLE_TESTER)))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .isEnabled(false)
                .build();

        testerRepository.save(newTester);

        Confirmation confirmationToken = new Confirmation(newTester);
        confirmationTokenRepository.save(confirmationToken);

        String verificationUrl = "http://localhost:8083/v1/tester/confirm?token=" + confirmationToken.getConfirmationToken();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(testerDto.email());
        mailMessage.setSubject("Tivibu Testçi Kayıt Onayı.");
        mailMessage.setText("Merhaba, kayıt işleminizi tamamlamak için linke tıklayınız : " + verificationUrl);
        emailService.sendEmail(mailMessage);
    }

    public void confirmTester(String token) {

        Confirmation confirmationToken = confirmationTokenRepository.findByConfirmationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid confirmation token"));

        Tester testerToRegister = confirmationToken.getTester();
        if (testerRepository.existsByEmail(testerToRegister.getEmail())) {
            testerToRegister.setEnabled(true);
            testerRepository.save(testerToRegister);
        }
        confirmationTokenRepository.delete(confirmationToken);

    }

    public Tester getTesterById(Long testerId) {
        return testerRepository.findById(testerId)
                .orElseThrow(() -> new UsernameNotUniqueException(testerId + " numaralı testci bulunamadı."));
    }

    public Optional<Tester> getTesterByName(String name) {
        return testerRepository.findByUsername(name);

    }

    public TesterRequest createNewTesterRequest(TesterDto testerDto) {

        testerValidator.validateTesterId(testerDto.testerId());

        if (testerRepository.existsById(testerDto.testerId())) {
            throw new UsernameNotUniqueException("Bu sicil numarasıyla kayıtlı bir testci zaten bulunmakta!");
        }

        if (testerRequestRepository.existsById(testerDto.testerId())) {
            throw new UsernameNotUniqueException("Bu sicil numarasıyla daha önce bir kayıt isteğinde bulunulmuş!");
        }

        if (testerRepository.existsByUsername(testerDto.username())) {
            throw new UsernameNotUniqueException("Bu kullanıcı adı ile kayıtlı bir testci zaten bulunmakta!");
        }

        if (testerRequestRepository.existsByUsername(testerDto.username())) {
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

    public Tester createNewTesterByTesterRequest(TesterRequest testerRequest) {


        //checking if id is 6 digit or not
        testerValidator.validateTesterId(testerRequest.getTester_id());

        if (testerRepository.existsById(testerRequest.getTester_id())) {
            throw new IdNotUniqueException(testerRequest.getTester_id() + " sicil numaralı testci zaten bulunmakta.");
        }

        return Tester.builder()
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

    public Long getTesterId() {
        String loggedInUsername = WhoAuthenticated.whoIsAuthenticated();

        Tester loggedInTester = testerRepository.findByUsername(loggedInUsername).orElseThrow();

        return loggedInTester.getTester_id();
    }

    public void deleteTester(Long testerId) {

        testerRepository.deleteById(testerId);
    }
}
