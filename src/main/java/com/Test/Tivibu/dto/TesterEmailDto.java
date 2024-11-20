package com.Test.Tivibu.dto;

public record TesterEmailDto(
        Long testerId,
        String testerName,
        String username,
        String password,
        String email
) {
}
