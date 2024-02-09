package com.gmf.engineshop.assistant.module.enginefamily;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmf.engineshop.assistant.core.exception.AlreadyExistException;
import com.gmf.engineshop.assistant.core.exception.InvalidDataException;
import com.gmf.engineshop.assistant.core.exception.NotFoundException;
import com.gmf.engineshop.assistant.core.helper.ObjectMapper;
import com.gmf.engineshop.assistant.core.model.ServiceDTO;
import com.gmf.engineshop.assistant.module.enginefamily.dto.EngineFamilyDTO;

import lombok.NonNull;

@Service
@Transactional
public class EngineFamilyServiceImpl implements EngineFamilyService {

   private final EngineFamilyRepository<EngineFamilyDTO> engineFamilyRepository;

   public EngineFamilyServiceImpl(EngineFamilyRepository<EngineFamilyDTO> engineFamilyRepository) {
      this.engineFamilyRepository = engineFamilyRepository;
   }

   @Override
   public ServiceDTO<List<EngineFamilyDTO>> getAll(String status) {

      Boolean statusDeleted = status.equalsIgnoreCase("deleted") ? true : false;
      return new ServiceDTO<List<EngineFamilyDTO>>(
            engineFamilyRepository.findByDeletedIs(statusDeleted), "Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<EngineFamilyDTO> getById(@NonNull UUID id) {

      EngineFamilyDTO result = engineFamilyRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "No Data with id::" + id));
      return new ServiceDTO<EngineFamilyDTO>(
            result,
            "Get Data Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<Page<EngineFamilyDTO>> getAllPageAndSort(
         Integer currentPage,
         Integer totalItemsPerPage,
         String sortBy,
         String sortOrder,
         String status) {

      Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
      Pageable pageRequest = PageRequest.of(currentPage, totalItemsPerPage, sort);

      Page<EngineFamilyDTO> result;

      String statusDeleted = status.equalsIgnoreCase("ALL") ? "ALL"
            : status.equalsIgnoreCase("AVAILABLE") ? "AVAILABLE" : "DELETED";
      if (statusDeleted == "AVAILABLE") {
         result = engineFamilyRepository.findByDeletedIs(Boolean.FALSE, pageRequest);
      } else if (statusDeleted == "DELETED") {
         result = engineFamilyRepository.findByDeletedIs(Boolean.TRUE, pageRequest);
      } else {
         result = engineFamilyRepository.findAll(pageRequest);
      }

      return new ServiceDTO<Page<EngineFamilyDTO>>(result, "Success", HttpStatus.OK);

   }

   @SuppressWarnings("null")
   @Override
   public ServiceDTO<EngineFamilyDTO> create(
         EngineFamilyDTO request) throws IOException {

      EngineFamilyDTO isExist = engineFamilyRepository.findByName(request.getName());
      if (isExist != null) {
         throw new AlreadyExistException(
               "Data with name " + request.getName() + " Already Exist");
      }

      EngineFamilyDTO engineFamily = EngineFamilyDTO.builder()
            .id(UUID.randomUUID())
            .name(request.getName())
            .description(request.getDescription())
            .build();
      EngineFamilyDTO result;
      try {
         result = engineFamilyRepository.save(engineFamily);
         return new ServiceDTO<EngineFamilyDTO>(
               result,
               "Success",
               HttpStatus.CREATED);
      } catch (Exception e) {
         throw new NotFoundException(
               "No Data with name :: " + request.getName());
      }

   }

   @SuppressWarnings("null")
   @Override
   public ServiceDTO<EngineFamilyDTO> update(
         @NonNull UUID id,
         EngineFamilyDTO request) throws IOException {

      EngineFamilyDTO result = engineFamilyRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "No Data with id::" + id));

      ObjectMapper.map(request, result);

      return new ServiceDTO<>(
            engineFamilyRepository.save(result),
            "Update Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<EngineFamilyDTO> softDelete(@NonNull UUID id) throws IOException {

      EngineFamilyDTO result = engineFamilyRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "No Data with id::" + id));

      result.setDeleted(true);
      return new ServiceDTO<>(
            result,
            "Delete Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<EngineFamilyDTO> hardDelete(@NonNull UUID id) throws IOException {

      EngineFamilyDTO result = engineFamilyRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "No Data with id::" + id));

      if (result.getDeleted() == true) {
         engineFamilyRepository.deleteById(id);
         return new ServiceDTO<>(
               null,
               "Destroy Success",
               HttpStatus.OK);
      } else {
         throw new InvalidDataException(
               "Only deleted data will be destroy");
      }

   }

   @Override
   public ServiceDTO<EngineFamilyDTO> recover(@NonNull UUID id) throws IOException {

      EngineFamilyDTO result = engineFamilyRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "No Data with id::" + id));

      result.setDeleted(Boolean.FALSE);
      return new ServiceDTO<>(
            result,
            "Recover Success",
            HttpStatus.OK);

   }

}
