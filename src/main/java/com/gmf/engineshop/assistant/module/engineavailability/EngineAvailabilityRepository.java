package com.gmf.engineshop.assistant.module.engineavailability;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gmf.engineshop.assistant.module.engineavailability.dto.EngineAvailabilityDTO;

public interface EngineAvailabilityRepository<T extends EngineAvailabilityDTO> extends JpaRepository<T, UUID> {
   List<T> findByIsIncomingAndEngineId(Boolean isIncoming, UUID engineId);

   Page<T> findByIsIncomingAndEngineId(Boolean isIncoming, UUID engineId, Pageable pageable);

   Page<T> findByEngineId(UUID engineId, Pageable pageable);
}
