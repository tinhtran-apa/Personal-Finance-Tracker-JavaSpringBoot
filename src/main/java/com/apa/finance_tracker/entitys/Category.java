package com.apa.finance_tracker.entitys;
import com.apa.finance_tracker.enums.CategoryType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType type;

    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String icon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @PrePersist
    public void prePersit() {
        createdAt = LocalDateTime.now();
    }
}
