package com.apa.finance_tracker.dtos.responses;

import com.apa.finance_tracker.enums.RoleType;
import lombok.*;

import javax.management.relation.Role;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private RoleType role;
    private LocalDateTime createdAt;
}
