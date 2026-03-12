package com.mining.repository;

import com.mining.entity.Mineral;
import com.mining.entity.MineEnterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface MineralRepository extends JpaRepository<Mineral, Long> {

    Optional<Mineral> findByMineralCode(String mineralCode);

    List<Mineral> findByEnterprise(MineEnterprise enterprise);

    List<Mineral> findByStatus(Mineral.MiningStatus status);

    boolean existsByMineralCode(String mineralCode);
}
