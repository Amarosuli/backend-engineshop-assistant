package com.gmf.engineshop.assistant.module.enginemodel;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gmf.engineshop.assistant.module.enginemodel.dto.EngineModelDTO;

public interface EngineModelRepository<T extends EngineModelDTO> extends JpaRepository<T, UUID> {
   List<T> findByDeletedIs(Boolean isDeleted);

   Page<T> findByDeletedIs(Boolean isDeleted, Pageable pageable);

   T findByName(String name) throws IllegalArgumentException;
}
