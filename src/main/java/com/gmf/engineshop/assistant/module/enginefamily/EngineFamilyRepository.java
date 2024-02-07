package com.gmf.engineshop.assistant.module.enginefamily;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gmf.engineshop.assistant.module.enginefamily.dto.EngineFamilyDTO;

public interface EngineFamilyRepository<T extends EngineFamilyDTO> extends JpaRepository<T, UUID> {
   List<T> findByDeletedIs(Boolean isDeleted);

   Page<T> findByDeletedIs(Boolean isDeleted, Pageable pageable);

   T findByName(String name) throws IllegalArgumentException;
}
