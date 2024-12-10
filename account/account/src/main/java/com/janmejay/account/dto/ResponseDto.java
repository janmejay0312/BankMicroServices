package com.janmejay.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
@Schema(
        name = "Response",
        description = "Schema to hold Success Response Information"
)
public class ResponseDto {
    @Schema(
            description = "StatusCode", example = "200"
    )
    private String statusCode;

    @Schema(
            description = "Status Message", example = "OK"
    )
    private String statusMessage;
}
