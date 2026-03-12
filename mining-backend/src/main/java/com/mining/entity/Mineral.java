package com.mining.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "mineral")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mineral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String mineralCode;

    @Column(nullable = false, length = 100)
    private String mineralName;

    @Column(length = 50)
    private String mineralType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id", nullable = false)
    private MineEnterprise enterprise;

    @Column(name = "reserves")
    private BigDecimal reserves;

    @Column(name = "grade")
    private BigDecimal grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MiningStatus status;

    @Column(length = 500)
    private String remarks;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum MiningStatus {
        MINING("开采中"),
        EXPLORATION("勘探中"),
        CLOSED("已关闭");

        public final String label;

        MiningStatus(String label) {
            this.label = label;
        }
    }
}
