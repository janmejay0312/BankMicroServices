package com.janmejay.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data @AllArgsConstructor
@Schema(
        name = "Error Response",
        description = "Schema to hold Error Response Information"
)
public class ErrorResponseDto {

    @Schema(
            description = "API in which error Occurred", example = "/api/v1"
    )
    private String apiPath;

    @Schema(
            description = "Status Code", example = "500"
    )
    private HttpStatus errorStatus;

    @Schema(
            description = "Status Message", example = "Internal Server Error"
    )
    private String errorMessage;

    @Schema(
            description = "Error Time"
    )
    private LocalDateTime errorTime;
}
