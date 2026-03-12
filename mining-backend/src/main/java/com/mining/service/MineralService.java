package com.mining.service;

import com.mining.entity.Mineral;
import com.mining.entity.MineEnterprise;
import com.mining.repository.MineralRepository;
import com.mining.repository.MineEnterpriseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MineralService {

    private final MineralRepository mineralRepository;
    private final MineEnterpriseRepository enterpriseRepository;

    public Mineral create(Mineral mineral) {
        if (mineralRepository.existsByMineralCode(mineral.getMineralCode())) {
            throw new IllegalArgumentException("矿产编号已存在");
        }
        return mineralRepository.save(mineral);
    }

    public Mineral update(Long id, Mineral mineral) {
        Mineral existing = mineralRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("矿产不存在"));
        
        if (!existing.getMineralCode().equals(mineral.getMineralCode()) &&
            mineralRepository.existsByMineralCode(mineral.getMineralCode())) {
            throw new IllegalArgumentException("矿产编号已存在");
        }
        
        existing.setMineralCode(mineral.getMineralCode());
        existing.setMineralName(mineral.getMineralName());
        existing.setMineralType(mineral.getMineralType());
        existing.setReserves(mineral.getReserves());
        existing.setGrade(mineral.getGrade());
        existing.setStatus(mineral.getStatus());
        existing.setRemarks(mineral.getRemarks());
        
        return mineralRepository.save(existing);
    }

    public void delete(Long id) {
        mineralRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Mineral findById(Long id) {
        return mineralRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("矿产不存在"));
    }

    @Transactional(readOnly = true)
    public Mineral findByCode(String mineralCode) {
        return mineralRepository.findByMineralCode(mineralCode)
            .orElseThrow(() -> new RuntimeException("矿产不存在"));
    }

    @Transactional(readOnly = true)
    public List<Mineral> findAll() {
        return mineralRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Mineral> findByEnterprise(Long enterpriseId) {
        MineEnterprise enterprise = enterpriseRepository.findById(enterpriseId)
            .orElseThrow(() -> new RuntimeException("企业不存在"));
        return mineralRepository.findByEnterprise(enterprise);
    }

    @Transactional(readOnly = true)
    public List<Mineral> findByStatus(Mineral.MiningStatus status) {
        return mineralRepository.findByStatus(status);
    }
}
