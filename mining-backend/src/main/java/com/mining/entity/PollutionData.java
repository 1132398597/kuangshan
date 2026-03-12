package com.mining.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pollution_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PollutionData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id", nullable = false)
    private MineEnterprise enterprise;

    @Column(nullable = false)
    private BigDecimal pollutionIndex;

    @Column(name = "pm25")
    private BigDecimal pm25;

    @Column(name = "pm10")
    private BigDecimal pm10;

    @Column(name = "so2")
    private BigDecimal so2;

    @Column(name = "no2")
    private BigDecimal no2;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PollutionLevel level;

    @Column(nullable = false)
    private LocalDateTime recordTime;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public enum PollutionLevel {
        LOW("低"),
        MEDIUM("中"),
        HIGH("高");

        public final String label;

        PollutionLevel(String label) {
            this.label = label;
        }
    }
}
