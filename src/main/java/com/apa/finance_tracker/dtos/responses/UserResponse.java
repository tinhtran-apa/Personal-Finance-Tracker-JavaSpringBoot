package com.apa.finance_tracker.dtos.responses;

import com.apa.finance_tracker.enums.RoleType;
import lombok.*;

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
}
