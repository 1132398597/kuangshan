package com.mining.controller;

import com.mining.entity.PollutionData;
import com.mining.service.PollutionDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pollution")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PollutionDataController {

    private final PollutionDataService service;

    @GetMapping
    public ResponseEntity<List<PollutionData>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/enterprise/{enterpriseId}")
    public ResponseEntity<List<PollutionData>> findByEnterprise(@PathVariable Long enterpriseId) {
        return ResponseEntity.ok(service.findByEnterprise(enterpriseId));
    }

    @GetMapping("/enterprise/{enterpriseId}/latest")
    public ResponseEntity<PollutionData> getLatest(@PathVariable Long enterpriseId) {
        return ResponseEntity.ok(service.getLatest(enterpriseId));
    }

    @GetMapping("/enterprise/{enterpriseId}/range")
    public ResponseEntity<List<PollutionData>> findByRange(
        @PathVariable Long enterpriseId,
        @RequestParam LocalDateTime startTime,
        @RequestParam LocalDateTime endTime
    ) {
        return ResponseEntity.ok(service.findByEnterpriseBetween(enterpriseId, startTime, endTime));
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<List<PollutionData>> findByLevel(@PathVariable PollutionData.PollutionLevel level) {
        return ResponseEntity.ok(service.findByLevel(level));
    }

    @PostMapping
    public ResponseEntity<PollutionData> record(@RequestBody PollutionData pollutionData) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.record(pollutionData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
