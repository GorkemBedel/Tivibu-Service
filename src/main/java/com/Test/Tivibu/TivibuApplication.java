package com.Test.Tivibu;

import com.Test.Tivibu.model.Role;
import com.Test.Tivibu.model.users.Admin;
import com.Test.Tivibu.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.List;

@SpringBootApplication
public class TivibuApplication implements CommandLineRunner {

	private final AdminRepository adminRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public TivibuApplication(AdminRepository adminRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.adminRepository = adminRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(TivibuApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {



		Admin admin = Admin.builder()
				.admin_id(1L)
				.name("Big Admin")
				.username("Big Admin")
				.password(bCryptPasswordEncoder.encode("Big Admin"))
				.authorities(new HashSet<>(List.of(Role.ROLE_ADMIN)))
				.role(Role.ROLE_ADMIN)
				.build();
		adminRepository.save(admin);

	}

}
