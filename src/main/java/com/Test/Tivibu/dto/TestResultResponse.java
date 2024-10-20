package com.Test.Tivibu.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nullable;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TestResultResponse(

        @Nullable
        ResultDto v1_result,
        @Nullable
        ResultDto v2_result,
        @Nullable
        List<SubTestResultDto> subTestsResults,
        String deviceType,
        String deviceVersion,
        String testName,
        String testerName
) {
}
