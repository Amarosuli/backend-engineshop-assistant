package com.gmf.engineshop.assistant.module.engine;

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

import com.gmf.engineshop.assistant.core.exception.AlreadyExistException;
import com.gmf.engineshop.assistant.core.exception.InvalidDataException;
import com.gmf.engineshop.assistant.core.exception.NotFoundException;
import com.gmf.engineshop.assistant.core.helper.ObjectMapper;
import com.gmf.engineshop.assistant.core.model.ServiceDTO;
import com.gmf.engineshop.assistant.module.customer.CustomerService;
import com.gmf.engineshop.assistant.module.customer.dto.CustomerDTO;
import com.gmf.engineshop.assistant.module.engine.dto.EngineDTO;
import com.gmf.engineshop.assistant.module.engine.dto.EngineRequestDTO;
import com.gmf.engineshop.assistant.module.enginemodel.EngineModelService;
import com.gmf.engineshop.assistant.module.enginemodel.dto.EngineModelDTO;

import lombok.NonNull;

@Service
@Transactional
public class EngineServiceImpl implements EngineService {

   @Autowired
   EngineModelService engineModelService;

   @Autowired
   CustomerService customerService;

   private final EngineRepository<EngineDTO> engineRepository;

   public EngineServiceImpl(EngineRepository<EngineDTO> engineRepository) {
      this.engineRepository = engineRepository;
   }

   @Override
   public ServiceDTO<List<EngineDTO>> getAll(String status) {

      Boolean statusDeleted = status.equalsIgnoreCase("deleted") ? true : false;
      return new ServiceDTO<List<EngineDTO>>(
            engineRepository.findByDeletedIs(statusDeleted),
            "Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<EngineDTO> getById(@NonNull UUID id) {

      EngineDTO result = engineRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "Not Data with id :: " + id));
      return new ServiceDTO<EngineDTO>(
            result,
            "Get Data Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<Page<EngineDTO>> getAllPageAndSort(Integer currentPage, Integer totalItemsPerPage, String sortBy,
         String sortOrder, String status) {

      Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

      Pageable pageRequest = PageRequest.of(currentPage, totalItemsPerPage, sort);

      Page<EngineDTO> result;

      String statusDeleted = status.equalsIgnoreCase("ALL") ? "ALL"
            : status.equalsIgnoreCase("AVAILABLE") ? "AVAILABLE" : "DELETED";

      if (statusDeleted == "AVAILABLE") {
         result = engineRepository.findByDeletedIs(Boolean.FALSE, pageRequest);
      } else if (statusDeleted == "DELETED") {
         result = engineRepository.findByDeletedIs(Boolean.TRUE, pageRequest);
      } else {
         result = engineRepository.findAll(pageRequest);
      }

      return new ServiceDTO<Page<EngineDTO>>(
            result,
            "Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<EngineDTO> create(EngineDTO request) throws IOException {
      throw new UnsupportedOperationException("Unimplemented method 'softDelete'");
   }

   @SuppressWarnings("null")
   @Override
   public ServiceDTO<EngineDTO> update(@NonNull UUID id, EngineDTO request) throws IOException {
      EngineDTO result = engineRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "No Engine Data with id :: " + id));

      ObjectMapper.map(request, result);

      return new ServiceDTO<>(
            engineRepository.save(result),
            "Update Success",
            HttpStatus.OK);
   }

   @Override
   public ServiceDTO<EngineDTO> softDelete(@NonNull UUID id) throws IOException {

      EngineDTO result = engineRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "Not Data with id :: " + id));

      result.setDeleted(Boolean.TRUE);
      return new ServiceDTO<>(
            result,
            "Delete Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<EngineDTO> hardDelete(@NonNull UUID id) throws IOException {

      EngineDTO result = engineRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "Not Data with id :: " + id));

      if (result.getDeleted() == true) {
         engineRepository.deleteById(id);
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
   public ServiceDTO<EngineDTO> recover(@NonNull UUID id) throws IOException {

      EngineDTO result = engineRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "Not Data with id :: " + id));

      result.setDeleted(Boolean.FALSE);
      return new ServiceDTO<>(
            result,
            "Recover Success",
            HttpStatus.OK);

   }

   @SuppressWarnings("null")
   @Override
   public ServiceDTO<EngineDTO> create(EngineRequestDTO request) throws IOException {

      EngineModelDTO engineModel = engineModelService.getById(request.getEngineModelId()).getData();
      CustomerDTO customer = customerService.getById(request.getCustomerId()).getData();

      EngineDTO isEngineExist = engineRepository.findByEsn(request.getEsn());
      if (isEngineExist != null) {
         throw new AlreadyExistException(
               "Engine Data with ESN " + request.getEsn() + " is Already Exist");
      }

      EngineDTO result = EngineDTO
            .builder()
            .esn(request.getEsn())
            .config(request.getConfig())
            .notes(request.getNotes())
            .isPreservable(request.getIsPreservable())
            .isServiceable(request.getIsServiceable())
            .engineModel(engineModel)
            .customer(customer)
            .build();

      return new ServiceDTO<EngineDTO>(
            engineRepository.save(result),
            "Success",
            HttpStatus.CREATED);
   }

}
