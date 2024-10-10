package com.Test.Tivibu.dto;


import java.util.List;
import java.util.Optional;

public record TestResultDto(
        ResultDto v1_result,
        ResultDto v2_result,
        //Optional<List<ResultDto>> subTestsResults,
        Long deviceId,
        Long testId

) {
}
