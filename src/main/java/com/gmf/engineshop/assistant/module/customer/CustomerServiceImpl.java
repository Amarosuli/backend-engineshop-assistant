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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gmf.engineshop.assistant.core.base.ServiceDTO;
import com.gmf.engineshop.assistant.core.helper.ObjectMapper;
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
   public ServiceDTO<List<CustomerDTO>> getAll(String status) {

      Boolean statusDeleted = status.equalsIgnoreCase("deleted") ? true : false;
      return new ServiceDTO<List<CustomerDTO>>(customerRepository.findByDeletedIs(statusDeleted), "Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<CustomerDTO> getById(@NonNull UUID id) {

      Optional<CustomerDTO> result;
      try {
         result = customerRepository.findById(id);
      } catch (Exception e) {
         throw new Error(e);
      }

      if (result.isPresent()) {
         return new ServiceDTO<CustomerDTO>(result.get(), "Success", HttpStatus.OK);
      }
      return new ServiceDTO<CustomerDTO>(null, "Success", HttpStatus.BAD_REQUEST);
   }

   @Override
   public ServiceDTO<Page<CustomerDTO>> getAllPageAndSort(
         Integer currentPage,
         Integer totalItemsPerPage,
         String sortBy,
         String sortOrder,
         String status) {

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

      return new ServiceDTO<Page<CustomerDTO>>(result, "Success", HttpStatus.OK);
   }

   @SuppressWarnings("null")
   @Override
   public ServiceDTO<CustomerDTO> create(
         @NonNull CustomerDTO request) throws IOException {

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
         return new ServiceDTO<CustomerDTO>(null, "Failed", HttpStatus.MULTI_STATUS);
      }
      return new ServiceDTO<CustomerDTO>(result, "Success", HttpStatus.CREATED);

   }

   @SuppressWarnings("null")
   @Override
   public ServiceDTO<CustomerDTO> update(
         @NonNull UUID id,
         CustomerDTO request) throws IOException {
      Optional<CustomerDTO> customerOptional;

      try {
         customerOptional = customerRepository.findById(id);
      } catch (Exception e) {
         return new ServiceDTO<>(null, "Data with id " + id + "Not Found", HttpStatus.NOT_FOUND);
      }

      if (customerOptional.isPresent()) {
         CustomerDTO destination = customerOptional.get();

         ObjectMapper.map(request, destination);

         return new ServiceDTO<>(customerRepository.save(destination), "Update Success",
               HttpStatus.OK);
      }
      return new ServiceDTO<>(null, "Failed", HttpStatus.BAD_REQUEST);
   }

   @Override
   public ServiceDTO<CustomerDTO> softDelete(@NonNull UUID id) throws IOException {
      Optional<CustomerDTO> customerOptional;

      try {
         customerOptional = customerRepository.findById(id);
      } catch (Exception e) {
         return new ServiceDTO<>(null, "Data with id " + id + "Not Found", HttpStatus.NOT_FOUND);
      }

      if (customerOptional.isPresent()) {
         CustomerDTO destination = customerOptional.get();
         destination.setDeleted(true);
         return new ServiceDTO<>(destination, "Delete Success", HttpStatus.OK);
      }

      return new ServiceDTO<>(null, "Failed", HttpStatus.BAD_REQUEST);
   }

   @Override
   public ServiceDTO<CustomerDTO> hardDelete(@NonNull UUID id) throws IOException {
      Optional<CustomerDTO> customerOptional;

      try {
         customerOptional = customerRepository.findById(id);
      } catch (Exception e) {
         return new ServiceDTO<>(null, "Data with id " + id + "Not Found", HttpStatus.NOT_FOUND);
      }

      if (customerOptional.isPresent() && customerOptional.get().getDeleted() == true) {
         customerRepository.deleteById(id);
         return new ServiceDTO<>(null, "Destroy Success", HttpStatus.OK);
      }
      return new ServiceDTO<>(null, "Failed, Only deleted data will be destroy",
            HttpStatus.BAD_REQUEST);

   }

   @Override
   public ServiceDTO<CustomerDTO> recover(@NonNull UUID id) throws IOException {
      Optional<CustomerDTO> customerOptional;

      try {
         customerOptional = customerRepository.findById(id);
      } catch (Exception e) {
         return new ServiceDTO<>(null, "Data with id " + id + "Not Found", HttpStatus.NOT_FOUND);
      }

      if (customerOptional.isPresent()) {
         CustomerDTO destination = customerOptional.get();
         destination.setDeleted(false);
         return new ServiceDTO<>(destination, "Delete Success", HttpStatus.OK);
      }
      return new ServiceDTO<>(null, "Failed", HttpStatus.BAD_REQUEST);
   }

}
