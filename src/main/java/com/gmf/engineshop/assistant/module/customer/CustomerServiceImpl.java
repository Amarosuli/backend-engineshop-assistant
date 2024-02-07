package com.gmf.engineshop.assistant.module.customer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import com.gmf.engineshop.assistant.core.base.HttpResponseDTO;
import com.gmf.engineshop.assistant.core.base.PageAndSortResultDTO;
import com.gmf.engineshop.assistant.core.base.ResultDTO;
import com.gmf.engineshop.assistant.core.helper.ObjectMapper;
import com.gmf.engineshop.assistant.core.utility.ResponseHandler;
import com.gmf.engineshop.assistant.module.customer.dto.CustomerDTO;

import lombok.NonNull;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

   private final CustomerRepository<CustomerDTO> customerRepository;

   public CustomerServiceImpl(CustomerRepository<CustomerDTO> customerRepository) {
      this.customerRepository = customerRepository;
   }

   @Override
   @GetMapping
   public ResponseEntity<HttpResponseDTO<ResultDTO<CustomerDTO>>> getAll(String status) {

      Boolean statusDeleted = status.equalsIgnoreCase("deleted") ? true : false;
      List<CustomerDTO> result = customerRepository.findByDeletedIs(statusDeleted);
      return ResponseHandler.getResponseListEntity(result, "Success", HttpStatus.OK);

   }

   @Override
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> getById(@NonNull UUID id) {

      Optional<CustomerDTO> result;
      try {
         result = customerRepository.findById(id);
      } catch (Exception e) {
         // TODO: handle exception
         // return ResponseHandler.getResponseEntity(null, "Error",
         // HttpStatus.MULTI_STATUS);
         throw new Error(e);
      }

      if (result.isPresent()) {
         return ResponseHandler.getResponseEntity(result.get(), "Success", HttpStatus.OK);
      }
      return ResponseHandler.getResponseEntity(null, "Not Found", HttpStatus.NOT_FOUND);
   }

   @Override
   public ResponseEntity<HttpResponseDTO<PageAndSortResultDTO<CustomerDTO>>> getAllPageAndSort(Integer currentPage,
         Integer totalItemsPerPage, String sortBy, String sortOrder, String status) {

      Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
      Pageable pageRequest = PageRequest.of(currentPage, totalItemsPerPage, sort);

      Page<CustomerDTO> result;

      String statusDeleted = status.equalsIgnoreCase("ALL") ? "ALL"
            : status.equalsIgnoreCase("AVAILABLE") ? "AVAILABLE" : "DELETED";
      if (statusDeleted == "AVAILABLE") {
         result = customerRepository.findByDeletedIs(Boolean.FALSE, pageRequest);
      } else if (statusDeleted == "DELETED") {
         result = customerRepository.findByDeletedIs(Boolean.TRUE, pageRequest);
      } else {
         result = customerRepository.findAll(pageRequest);
      }
      return ResponseHandler.getResponsePage(result, "Success", HttpStatus.OK);
   }

   @SuppressWarnings("null")
   @Override
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> create(@NonNull CustomerDTO request)
         throws IOException {

      CustomerDTO customer = CustomerDTO.builder()
            .id(UUID.randomUUID())
            .name(request.getName())
            .description(request.getDescription())
            .email(request.getEmail())
            .address(request.getAddress())
            .alias(request.getAlias())
            .build();
      CustomerDTO result;
      try {
         result = customerRepository.save(customer);
      } catch (Exception e) {
         // TODO: handle exception
         return ResponseHandler.getResponseEntity(null, "Failed", HttpStatus.MULTI_STATUS);
      }
      return ResponseHandler.getResponseEntity(result, "Success", HttpStatus.CREATED);
   }

   @SuppressWarnings("null")
   @Override
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> update(@NonNull UUID id, CustomerDTO request)
         throws IOException {
      Optional<CustomerDTO> customerOptional;

      try {
         customerOptional = customerRepository.findById(id);
      } catch (Exception e) {
         return ResponseHandler.getResponseEntity(null, "Data with id " + id + "Not Found", HttpStatus.NOT_FOUND);
      }

      if (customerOptional.isPresent()) {
         CustomerDTO destination = customerOptional.get();

         ObjectMapper.map(request, destination);

         return ResponseHandler.getResponseEntity(
               customerRepository.save(destination), "Update Success",
               HttpStatus.OK);
      }

      return ResponseHandler.getResponseEntity(null, "Failed", HttpStatus.BAD_REQUEST);
   }

   @Override
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> softDelete(@NonNull UUID id) throws IOException {
      Optional<CustomerDTO> customerOptional;

      try {
         customerOptional = customerRepository.findById(id);
      } catch (Exception e) {
         return ResponseHandler.getResponseEntity(null, "Data with id " + id + "Not Found", HttpStatus.NOT_FOUND);
      }

      if (customerOptional.isPresent()) {
         CustomerDTO destination = customerOptional.get();
         destination.setDeleted(true);
         return ResponseHandler.getResponseEntity(
               customerRepository.save(destination), "Delete Success",
               HttpStatus.OK);
      }

      return ResponseHandler.getResponseEntity(null, "Failed", HttpStatus.BAD_REQUEST);
   }

   @Override
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> hardDelete(@NonNull UUID id) throws IOException {
      Optional<CustomerDTO> customerOptional;

      try {
         customerOptional = customerRepository.findById(id);
      } catch (Exception e) {
         return ResponseHandler.getResponseEntity(null, "Data with id " + id + "Not Found", HttpStatus.NOT_FOUND);
      }

      if (customerOptional.isPresent() && customerOptional.get().getDeleted() == true) {
         customerRepository.deleteById(id);
         return ResponseHandler.getResponseEntity(null, "Destroy Success",
               HttpStatus.OK);
      }

      return ResponseHandler.getResponseEntity(null, "Failed, Only deleted data will be destroy",
            HttpStatus.BAD_REQUEST);
   }

   @Override
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> recover(@NonNull UUID id) throws IOException {
      Optional<CustomerDTO> customerOptional;

      try {
         customerOptional = customerRepository.findById(id);
      } catch (Exception e) {
         return ResponseHandler.getResponseEntity(null, "Data with id " + id + "Not Found", HttpStatus.NOT_FOUND);
      }

      if (customerOptional.isPresent()) {
         CustomerDTO destination = customerOptional.get();
         destination.setDeleted(false);
         return ResponseHandler.getResponseEntity(
               customerRepository.save(destination), "Recover Success",
               HttpStatus.OK);
      }

      return ResponseHandler.getResponseEntity(null, "Failed", HttpStatus.BAD_REQUEST);
   }

}
