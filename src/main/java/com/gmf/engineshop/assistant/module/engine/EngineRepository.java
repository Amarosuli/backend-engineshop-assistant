package com.gmf.engineshop.assistant.module.engine;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gmf.engineshop.assistant.module.engine.dto.EngineDTO;

public interface EngineRepository<T extends EngineDTO> extends JpaRepository<T, UUID> {
   List<T> findByDeletedIs(Boolean isDeleted);

   Page<T> findByDeletedIs(Boolean IsDeleted, Pageable pageable);

   T findByEsn(String esn) throws IllegalArgumentException;
}
