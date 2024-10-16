package com.Test.Tivibu.dto;

public record SubTestResultDto(
        Boolean v1_isOk,
        String v1_comment,

        Boolean v2_isOk,
        String v2_comment
) {
}
