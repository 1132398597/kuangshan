package com.mining.repository;

import com.mining.entity.MineEnterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface MineEnterpriseRepository extends JpaRepository<MineEnterprise, Long> {

    Optional<MineEnterprise> findByEnterpriseCode(String enterpriseCode);

    List<MineEnterprise> findByStatus(MineEnterprise.EnterpriseStatus status);

    boolean existsByEnterpriseCode(String enterpriseCode);
}
