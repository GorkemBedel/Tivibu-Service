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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security, HandlerMappingIntrospector introspector) throws Exception{

        MvcRequestMatcher.Builder mvcRequestBuilder = new MvcRequestMatcher.Builder(introspector);

        security
                .csrf(AbstractHttpConfigurer::disable)
                .headers(x -> x.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(x -> x
                        .requestMatchers(mvcRequestBuilder.pattern("/v1/admin/**")).hasRole(Role.ROLE_ADMIN.getValue())
//                        .requestMatchers(mvcRequestBuilder.pattern("/v1/test/createLaborantRequest")).permitAll()
//                        .requestMatchers(mvcRequestBuilder.pattern("/v1/patient/**")).hasRole(Role.ROLE_USER.getValue())
//                        .requestMatchers(mvcRequestBuilder.pattern("/v1/laborant/**")).hasAnyRole(Role.ROLE_ADMIN.getValue(), Role.ROLE_LABORANT.getValue())
//                        .requestMatchers(mvcRequestBuilder.pattern("/v1/report/**")).hasAnyRole(Role.ROLE_ADMIN.getValue(), Role.ROLE_LABORANT.getValue())
//                        .requestMatchers(mvcRequestBuilder.pattern("/v1/admin/**")).hasRole(Role.ROLE_ADMIN.getValue())
                        .anyRequest().authenticated()


                )
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return security.build();
    }
}
