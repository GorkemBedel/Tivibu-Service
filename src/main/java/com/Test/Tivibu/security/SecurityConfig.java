package com.Test.Tivibu.security;


import com.Test.Tivibu.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    private CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security, HandlerMappingIntrospector introspector) throws Exception {

        MvcRequestMatcher.Builder mvcRequestBuilder = new MvcRequestMatcher.Builder(introspector);

        security
                .csrf(AbstractHttpConfigurer::disable)
                .headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(x -> x
                        .requestMatchers(mvcRequestBuilder.pattern("/v1/admin/**")).hasRole(Role.ROLE_ADMIN.getValue())
                        .requestMatchers(mvcRequestBuilder.pattern("/v1/device/**")).hasAnyRole(Role.ROLE_TESTER.getValue(), Role.ROLE_ADMIN.getValue())
                        .requestMatchers(mvcRequestBuilder.pattern("/v1/test/**")).hasAnyRole(Role.ROLE_ADMIN.getValue(), Role.ROLE_TESTER.getValue())
                        .requestMatchers(mvcRequestBuilder.pattern("/v1/testResult/**")).hasAnyRole(Role.ROLE_TESTER.getValue(), Role.ROLE_ADMIN.getValue())
                        .requestMatchers(mvcRequestBuilder.pattern("/v1/tester/**")).permitAll()
                        .requestMatchers(mvcRequestBuilder.pattern("/v1/tester/createTesterRequest")).permitAll()
                        .requestMatchers(mvcRequestBuilder.pattern("/v1/tester/register")).permitAll()
                        .requestMatchers(mvcRequestBuilder.pattern("/v1/tester/confirm**")).permitAll()
                        .requestMatchers(mvcRequestBuilder.pattern("/login.html")).permitAll()
                        .requestMatchers(mvcRequestBuilder.pattern("/register.html")).permitAll()
                        .requestMatchers(mvcRequestBuilder.pattern("/AdminPanel.html")).hasRole(Role.ROLE_ADMIN.getValue())
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Statik dosyalara izin ver
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html") // Özel giriş sayfası URL'si
                        .loginProcessingUrl("/perform_login") // Giriş işlemlerinin yapılacağı URL
                        .defaultSuccessUrl("/index.html", true) // Başarılı giriş sonrası yönlendirme
                        .failureUrl("/login?error=true") // Hatalı giriş durumunda yönlendirilecek URL
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler(customAccessDeniedHandler) // Custom AccessDeniedHandler
                )
                .httpBasic(Customizer.withDefaults());

        return security.build();
    }
}
