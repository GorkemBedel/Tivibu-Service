package com.Test.Tivibu.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TestResultDto(
        ResultDto v1_result,
        ResultDto v2_result,
        Optional<List<SubTestResultDto>> subTestsResults,
        Long deviceId,
        Long testId,
        Long testerId

) {
}
