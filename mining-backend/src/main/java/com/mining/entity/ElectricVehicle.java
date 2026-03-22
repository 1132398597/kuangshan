package com.mining.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "electric_vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectricVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String vehicleCode;

    @Column(nullable = false, length = 50)
    private String brand;

    @Column(length = 100)
    private String model;

    @Column(length = 50)
    private String licensePlate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enterprise_id")
    private MineEnterprise enterprise;

    @Column(length = 50)
    private String driverName;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "battery_capacity")
    private Double batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status;

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

    public enum VehicleStatus {
        ACTIVE("正常"),
        MAINTENANCE("维修中"),
        SCRAPPED("已报废");

        public final String label;

        VehicleStatus(String label) {
            this.label = label;
        }
    }
}
