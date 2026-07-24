package com.apa.finance_tracker.dtos.requests;

import com.apa.finance_tracker.constants.ValidationMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @Email(message = ValidationMessage.USER_EMAIL_INVALID)
    @NotBlank(message = ValidationMessage.USER_EMAIL_REQUIRED)
    private String email;

    @NotBlank(message = ValidationMessage.USER_PASSWORD_REQUIRED)
    @Size(min = 8, message = ValidationMessage.USER_PASSWORD_SIZE)
    private String password;
}
