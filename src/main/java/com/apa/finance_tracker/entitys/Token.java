package com.apa.finance_tracker.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "access_token")
    private String accessToken;

    @Column(nullable = false, name = "refresh_token")
    private String refreshToken;

    @Column(nullable = false)
    private boolean revoke = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_user",nullable = false)
    private User user;

    @Column(nullable = false, name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersit() {
        createdAt = LocalDateTime.now();
    }
}
