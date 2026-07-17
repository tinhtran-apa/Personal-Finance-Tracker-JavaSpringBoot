package com.apa.finance_tracker.dtos.responses;

import com.apa.finance_tracker.constants.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private ErrorCode errorCode;
    private String message;

    public ErrorResponse(
            LocalDateTime timestamp,
            int status,
            ErrorCode errorCode,
            String message
    ) {
        this.timestamp = timestamp;
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
}
