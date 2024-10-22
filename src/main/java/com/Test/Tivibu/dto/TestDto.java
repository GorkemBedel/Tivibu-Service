package com.Test.Tivibu.dto;

import java.util.List;
import java.util.Optional;

public record TestDto(
        String testName,
        Optional<List<String>> subTests,
        String type
) {
}
