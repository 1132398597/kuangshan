package com.mining.service;

import com.mining.entity.MineEnterprise;
import com.mining.repository.MineEnterpriseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MineEnterpriseService {

    private final MineEnterpriseRepository repository;

    public MineEnterprise create(MineEnterprise enterprise) {
        if (repository.existsByEnterpriseCode(enterprise.getEnterpriseCode())) {
            throw new IllegalArgumentException("企业编号已存在");
        }
        return repository.save(enterprise);
    }

    public MineEnterprise update(Long id, MineEnterprise enterprise) {
        MineEnterprise existing = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("企业不存在"));
        
        if (!existing.getEnterpriseCode().equals(enterprise.getEnterpriseCode()) &&
            repository.existsByEnterpriseCode(enterprise.getEnterpriseCode())) {
            throw new IllegalArgumentException("企业编号已存在");
        }
        
        existing.setEnterpriseCode(enterprise.getEnterpriseCode());
        existing.setEnterpriseName(enterprise.getEnterpriseName());
        existing.setLocation(enterprise.getLocation());
        existing.setContactPerson(enterprise.getContactPerson());
        existing.setContactPhone(enterprise.getContactPhone());
        existing.setStatus(enterprise.getStatus());
        
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public MineEnterprise findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("企业不存在"));
    }

    @Transactional(readOnly = true)
    public MineEnterprise findByCode(String enterpriseCode) {
        return repository.findByEnterpriseCode(enterpriseCode)
            .orElseThrow(() -> new RuntimeException("企业不存在"));
    }

    @Transactional(readOnly = true)
    public List<MineEnterprise> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<MineEnterprise> findByStatus(MineEnterprise.EnterpriseStatus status) {
        return repository.findByStatus(status);
    }
}
