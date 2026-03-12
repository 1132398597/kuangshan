package com.mining.service;

import com.mining.entity.PollutionData;
import com.mining.entity.MineEnterprise;
import com.mining.repository.PollutionDataRepository;
import com.mining.repository.MineEnterpriseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PollutionDataService {

    private final PollutionDataRepository pollutionRepository;
    private final MineEnterpriseRepository enterpriseRepository;

    public PollutionData record(PollutionData pollutionData) {
        return pollutionRepository.save(pollutionData);
    }

    @Transactional(readOnly = true)
    public List<PollutionData> findByEnterprise(Long enterpriseId) {
        MineEnterprise enterprise = enterpriseRepository.findById(enterpriseId)
            .orElseThrow(() -> new RuntimeException("企业不存在"));
        return pollutionRepository.findByEnterprise(enterprise);
    }

    @Transactional(readOnly = true)
    public List<PollutionData> findByEnterpriseBetween(Long enterpriseId, LocalDateTime startTime, LocalDateTime endTime) {
        MineEnterprise enterprise = enterpriseRepository.findById(enterpriseId)
            .orElseThrow(() -> new RuntimeException("企业不存在"));
        return pollutionRepository.findByEnterpriseAndRecordTimeBetween(enterprise, startTime, endTime);
    }

    @Transactional(readOnly = true)
    public PollutionData getLatest(Long enterpriseId) {
        MineEnterprise enterprise = enterpriseRepository.findById(enterpriseId)
            .orElseThrow(() -> new RuntimeException("企业不存在"));
        return pollutionRepository.findTopByEnterpriseOrderByRecordTimeDesc(enterprise);
    }

    @Transactional(readOnly = true)
    public List<PollutionData> findByLevel(PollutionData.PollutionLevel level) {
        return pollutionRepository.findByLevel(level);
    }

    @Transactional(readOnly = true)
    public List<PollutionData> findAll() {
        return pollutionRepository.findAll();
    }

    public void delete(Long id) {
        pollutionRepository.deleteById(id);
    }
}
