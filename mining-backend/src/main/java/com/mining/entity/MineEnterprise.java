package com.mining.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "mine_enterprise")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MineEnterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String enterpriseCode;

    @Column(nullable = false, length = 100)
    private String enterpriseName;

    @Column(length = 100)
    private String location;

    @Column(length = 50)
    private String contactPerson;

    @Column(length = 20)
    private String contactPhone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnterpriseStatus status;

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

    public enum EnterpriseStatus {
        ACTIVE("正常"),
        INACTIVE("停运"),
        MAINTENANCE("维护");

        public final String label;

        EnterpriseStatus(String label) {
            this.label = label;
        }
    }
}
