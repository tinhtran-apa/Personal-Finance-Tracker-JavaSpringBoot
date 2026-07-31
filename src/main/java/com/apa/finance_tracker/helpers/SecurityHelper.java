package com.apa.finance_tracker.helpers;

import com.apa.finance_tracker.constants.ErrorMessage;
import com.apa.finance_tracker.entitys.User;
import com.apa.finance_tracker.exceptions.resource.ResourceNotFoundException;
import com.apa.finance_tracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityHelper {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Long userId = Long.valueOf(authentication.getName());

        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND));
    }
}
