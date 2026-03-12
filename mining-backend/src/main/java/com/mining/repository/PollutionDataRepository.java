package com.mining.repository;

import com.mining.entity.PollutionData;
import com.mining.entity.MineEnterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PollutionDataRepository extends JpaRepository<PollutionData, Long> {

    List<PollutionData> findByEnterprise(MineEnterprise enterprise);

    List<PollutionData> findByEnterpriseAndRecordTimeBetween(
        MineEnterprise enterprise,
        LocalDateTime startTime,
        LocalDateTime endTime
    );

    PollutionData findTopByEnterpriseOrderByRecordTimeDesc(MineEnterprise enterprise);

    List<PollutionData> findByLevel(PollutionData.PollutionLevel level);
}
