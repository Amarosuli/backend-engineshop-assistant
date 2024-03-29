package com.gmf.engineshop.assistant.module.customer;

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
      return new ServiceDTO<List<CustomerDTO>>(
            customerRepository.findByDeletedIs(statusDeleted),
            "Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<CustomerDTO> getById(@NonNull UUID id) {
      CustomerDTO result = customerRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "No Data with id::" + id));
      return new ServiceDTO<CustomerDTO>(
            result,
            "Get Data Success",
            HttpStatus.OK);
   }

   @Override
   public ServiceDTO<Page<CustomerDTO>> getAllPageAndSort(
         Integer currentPage,
         Integer totalItemsPerPage,
         String sortBy,
         String sortOrder,
         String status) {

      Sort sort = sortOrder.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
      Pageable pageRequest = PageRequest.of(
            currentPage,
            totalItemsPerPage,
            sort);

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

      return new ServiceDTO<Page<CustomerDTO>>(
            result,
            "Success",
            HttpStatus.OK);
   }

   @SuppressWarnings("null")
   @Override
   public ServiceDTO<CustomerDTO> create(
         @NonNull CustomerDTO request) throws IOException {

      CustomerDTO isExist = customerRepository.findByName(request.getName());
      if (isExist != null) {
         throw new AlreadyExistException(
               "Data with name " + request.getName() + " Already Exist");
      }

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
         return new ServiceDTO<CustomerDTO>(
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
   public ServiceDTO<CustomerDTO> update(
         @NonNull UUID id,
         CustomerDTO request) throws IOException {

      CustomerDTO result = customerRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "No Data with id::" + id));

      ObjectMapper.map(request, result);

      return new ServiceDTO<>(
            customerRepository.save(result),
            "Update Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<CustomerDTO> softDelete(@NonNull UUID id) throws IOException {

      CustomerDTO result = customerRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "No Data with id::" + id));

      result.setDeleted(true);
      return new ServiceDTO<>(
            result,
            "Delete Success",
            HttpStatus.OK);

   }

   @Override
   public ServiceDTO<CustomerDTO> hardDelete(@NonNull UUID id) throws IOException {

      CustomerDTO result = customerRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "No Data with id::" + id));

      if (result.getDeleted() == true) {
         customerRepository.deleteById(id);
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
   public ServiceDTO<CustomerDTO> recover(@NonNull UUID id) throws IOException {

      CustomerDTO result = customerRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                  "No Data with id::" + id));

      result.setDeleted(false);
      return new ServiceDTO<>(
            result,
            "Delete Success",
            HttpStatus.OK);

   }

}
