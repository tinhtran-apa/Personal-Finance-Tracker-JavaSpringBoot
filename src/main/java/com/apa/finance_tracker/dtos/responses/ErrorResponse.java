package com.apa.finance_tracker.dtos.responses;

import com.apa.finance_tracker.constants.ErrorCode;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private ErrorCode errorCode;
    private String message;
}
