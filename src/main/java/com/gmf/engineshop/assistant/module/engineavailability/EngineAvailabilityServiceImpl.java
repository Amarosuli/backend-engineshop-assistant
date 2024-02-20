package com.gmf.engineshop.assistant.module.engineavailability;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmf.engineshop.assistant.core.exception.NotFoundException;
import com.gmf.engineshop.assistant.core.model.ServiceDTO;
import com.gmf.engineshop.assistant.module.engine.EngineService;
import com.gmf.engineshop.assistant.module.engine.dto.EngineDTO;
import com.gmf.engineshop.assistant.module.engineavailability.dto.EngineAvailabilityDTO;

import lombok.NonNull;

@Service
@Transactional
public class EngineAvailabilityServiceImpl implements EngineAvailabilityService {

   @Autowired
   EngineService engineService;

   private final EngineAvailabilityRepository<EngineAvailabilityDTO> engineAvailabilityRepository;

   public EngineAvailabilityServiceImpl(
         EngineAvailabilityRepository<EngineAvailabilityDTO> engineAvailabilityRepository) {
      this.engineAvailabilityRepository = engineAvailabilityRepository;
   }

   @Override
   public ServiceDTO<List<EngineAvailabilityDTO>> getAll(String status) {
      throw new UnsupportedOperationException("Unimplemented method 'getAll'");
   }

   @Override
   public ServiceDTO<EngineAvailabilityDTO> getById(@NonNull UUID id) {

      EngineAvailabilityDTO result = engineAvailabilityRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "No Engine Availability Data with id :: " + id));
      return new ServiceDTO<>(
            result,
            "Get Data Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<Page<EngineAvailabilityDTO>> getAllPageAndSort(Integer currentPage, Integer totalItemsPerPage,
         String sortBy, String sortOrder, String status) {

      Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

      Pageable pageRequest = PageRequest.of(currentPage, totalItemsPerPage, sort);

      Page<EngineAvailabilityDTO> result;

      // status will be use filter to Engine.status not EngineAvailability.status
      // 1. get all data from engine_availability
      // 2. check each status of engine wether is deleted or available
      // 3. return only engine which status is input from param. default "available"s

      result = engineAvailabilityRepository.findAll(pageRequest);
      return new ServiceDTO<Page<EngineAvailabilityDTO>>(
            result,
            "Success",
            HttpStatus.OK);
   }

   @SuppressWarnings("null")
   @Override
   public ServiceDTO<EngineAvailabilityDTO> create(EngineAvailabilityDTO request) throws IOException {

      EngineAvailabilityDTO engineAvailability = engineAvailabilityRepository.save(request);
      return new ServiceDTO<EngineAvailabilityDTO>(
            engineAvailability,
            "Success",
            HttpStatus.CREATED);

   }

   @Override
   public ServiceDTO<EngineAvailabilityDTO> update(@NonNull UUID id, EngineAvailabilityDTO request) throws IOException {
      throw new UnsupportedOperationException("Unimplemented method 'update'");
   }

   @Override
   public ServiceDTO<EngineAvailabilityDTO> softDelete(@NonNull UUID id) throws IOException {
      throw new UnsupportedOperationException("Unimplemented method 'softDelete'");
   }

   @Override
   public ServiceDTO<EngineAvailabilityDTO> hardDelete(@NonNull UUID id) throws IOException {
      throw new UnsupportedOperationException("Unimplemented method 'hardDelete'");
   }

   @Override
   public ServiceDTO<EngineAvailabilityDTO> recover(@NonNull UUID id) throws IOException {
      throw new UnsupportedOperationException("Unimplemented method 'recover'");
   }

   @SuppressWarnings("null")
   @Override
   public ServiceDTO<EngineAvailabilityDTO> createEngineIncoming(UUID engineId) throws IOException {

      // 1. Check engine is exist
      ServiceDTO<EngineDTO> engine = engineService.getById(engineId);

      // 2. Check latest data in engine_availability with this engineId, isIncoming
      // field should be false

      // 3. Create engine availability
      EngineAvailabilityDTO engineAvailability = engineAvailabilityRepository.save(
            EngineAvailabilityDTO
                  .builder()
                  .engine(engine.getData())
                  // .isIncoming(Boolean.TRUE) not necessary because default is TRUE
                  .build());

      return new ServiceDTO<EngineAvailabilityDTO>(
            engineAvailability,
            "Create Engine Incoming Success",
            HttpStatus.CREATED);

   }

   @SuppressWarnings("null")
   @Override
   public ServiceDTO<EngineAvailabilityDTO> createEngineOutgoing(UUID engineId) throws IOException {

      // 1. Check engine is exist
      ServiceDTO<EngineDTO> engine = engineService.getById(engineId);

      // 2. Check latest data in engine_availability with this engineId, isIncoming
      // field should be true

      // 3. Create engine availability
      EngineAvailabilityDTO engineAvailability = engineAvailabilityRepository.save(
            EngineAvailabilityDTO
                  .builder()
                  .engine(engine.getData())
                  .isIncoming(Boolean.FALSE)
                  .build());

      return new ServiceDTO<EngineAvailabilityDTO>(
            engineAvailability,
            "Create Engine Outgoing Success",
            HttpStatus.CREATED);

   }

   @Override
   public ServiceDTO<Page<EngineAvailabilityDTO>> getEngineHistory(UUID engineId, Integer currentPage,
         Integer totalItemsPerPage,
         String sortBy, String sortOrder, String status) {

      // 1. Check engine is exist
      engineService.getById(engineId);

      Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

      Pageable pageRequest = PageRequest.of(currentPage, totalItemsPerPage, sort);

      Page<EngineAvailabilityDTO> result;

      result = engineAvailabilityRepository.findByEngineId(engineId, pageRequest);

      return new ServiceDTO<Page<EngineAvailabilityDTO>>(
            result,
            "Success",
            HttpStatus.OK);
   }

}
