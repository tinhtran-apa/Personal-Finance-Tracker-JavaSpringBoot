package com.apa.finance_tracker.dtos.responses;

import com.apa.finance_tracker.enums.RoleType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private Long id;
    private String fullName;
    private String email;
    private RoleType role;
    private LocalDateTime createdAt;
}
