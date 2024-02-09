package com.gmf.engineshop.assistant.module.enginemodel;

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
import com.gmf.engineshop.assistant.module.enginemodel.dto.EngineModelDTO;

import lombok.NonNull;

@Service
@Transactional
public class EngineModelServiceImpl implements EngineModelService {

   private final EngineModelRepository<EngineModelDTO> engineModelRepository;

   public EngineModelServiceImpl(EngineModelRepository<EngineModelDTO> engineModelRepository) {
      this.engineModelRepository = engineModelRepository;
   }

   @Override
   public ServiceDTO<List<EngineModelDTO>> getAll(String status) {

      Boolean statusDeleted = status.equalsIgnoreCase("deleted") ? true : false;
      return new ServiceDTO<List<EngineModelDTO>>(
            engineModelRepository.findByDeletedIs(statusDeleted),
            "Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<EngineModelDTO> getById(@NonNull UUID id) {

      EngineModelDTO result = engineModelRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "Not Data with id :: " + id));
      return new ServiceDTO<EngineModelDTO>(
            result,
            "Get Data Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<Page<EngineModelDTO>> getAllPageAndSort(
         Integer currentPage,
         Integer totalItemsPerPage,
         String sortBy,
         String sortOrder,
         String status) {

      Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

      Pageable pageRequest = PageRequest.of(currentPage, totalItemsPerPage, sort);

      Page<EngineModelDTO> result;

      String statusDeleted = status.equalsIgnoreCase("ALL") ? "ALL"
            : status.equalsIgnoreCase("AVAILABLE") ? "AVAILABLE" : "DELETED";

      if (statusDeleted == "AVAILABLE") {
         result = engineModelRepository.findByDeletedIs(Boolean.FALSE, pageRequest);
      } else if (statusDeleted == "DELETED") {
         result = engineModelRepository.findByDeletedIs(Boolean.TRUE, pageRequest);
      } else {
         result = engineModelRepository.findAll(pageRequest);
      }

      return new ServiceDTO<Page<EngineModelDTO>>(
            result,
            "Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<EngineModelDTO> create(EngineModelDTO request) {

      EngineModelDTO isEngineModelExist = engineModelRepository.findByName(request.getName());
      if (isEngineModelExist != null) {
         throw new AlreadyExistException(
               "Engine Model Data with name " + request.getName() + " is Already Exist");
      }

      EngineModelDTO result;
      try {
         result = engineModelRepository.save(request);
         return new ServiceDTO<EngineModelDTO>(
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
   public ServiceDTO<EngineModelDTO> update(
         @NonNull UUID id,
         EngineModelDTO request) throws IOException {

      EngineModelDTO result = engineModelRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "Not Data with id :: " + id));

      ObjectMapper.map(request, result);

      return new ServiceDTO<>(
            engineModelRepository.save(result),
            "Update Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<EngineModelDTO> softDelete(@NonNull UUID id) throws IOException {

      EngineModelDTO result = engineModelRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "No Data with id :: " + id));

      result.setDeleted(Boolean.TRUE);
      return new ServiceDTO<>(
            result,
            "Delete Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<EngineModelDTO> hardDelete(@NonNull UUID id) throws IOException {

      EngineModelDTO result = engineModelRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "No Data with id :: " + id));

      if (result.getDeleted() == true) {
         engineModelRepository.deleteById(id);
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
   public ServiceDTO<EngineModelDTO> recover(@NonNull UUID id) throws IOException {

      EngineModelDTO result = engineModelRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "No Data with id :: " + id));

      result.setDeleted(Boolean.FALSE);
      return new ServiceDTO<>(
            result,
            "Recover Success",
            HttpStatus.OK);

   }

}
