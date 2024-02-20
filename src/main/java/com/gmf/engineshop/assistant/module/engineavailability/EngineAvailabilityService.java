package com.gmf.engineshop.assistant.module.engineavailability;

import java.io.IOException;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.gmf.engineshop.assistant.core.model.BaseService;
import com.gmf.engineshop.assistant.core.model.ServiceDTO;
import com.gmf.engineshop.assistant.module.engineavailability.dto.EngineAvailabilityDTO;

public abstract interface EngineAvailabilityService extends BaseService<EngineAvailabilityDTO> {
   ServiceDTO<EngineAvailabilityDTO> createEngineIncoming(UUID engineId) throws IOException;

   ServiceDTO<EngineAvailabilityDTO> createEngineOutgoing(UUID engineId) throws IOException;

   ServiceDTO<Page<EngineAvailabilityDTO>> getEngineHistory(
         UUID engineId,
         Integer currentPage,
         Integer totalItemsPerPage,
         String sortBy,
         String sortOrder,
         String status);
}
