package com.gmf.engineshop.assistant.module.customer;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gmf.engineshop.assistant.core.model.HttpResponseDTO;
import com.gmf.engineshop.assistant.core.model.PageAndSortResultDTO;
import com.gmf.engineshop.assistant.core.model.ResultDTO;
import com.gmf.engineshop.assistant.core.model.ServiceDTO;
import com.gmf.engineshop.assistant.core.utility.AppConstants;
import com.gmf.engineshop.assistant.core.utility.ResponseHandler;
import com.gmf.engineshop.assistant.module.customer.dto.CustomerDTO;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

   @Autowired
   private CustomerService customerService;

   @GetMapping
   public ResponseEntity<HttpResponseDTO<ResultDTO<CustomerDTO>>> getAllHandler() {
      ServiceDTO<List<CustomerDTO>> result = customerService.getAll("all");
      return ResponseHandler.getResponseListEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @GetMapping("/deleted")
   public ResponseEntity<HttpResponseDTO<ResultDTO<CustomerDTO>>> getAllDeletedHandler() {
      ServiceDTO<List<CustomerDTO>> result = customerService.getAll("deleted");
      return ResponseHandler.getResponseListEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @GetMapping("/available")
   public ResponseEntity<HttpResponseDTO<ResultDTO<CustomerDTO>>> getAllAvailableHandler() {
      ServiceDTO<List<CustomerDTO>> result = customerService.getAll("available");
      return ResponseHandler.getResponseListEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @GetMapping("/{id}")
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> getByIdHandler(@PathVariable UUID id) {
      ServiceDTO<CustomerDTO> result = customerService.getById(id);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @GetMapping("/pagination")
   public ResponseEntity<HttpResponseDTO<PageAndSortResultDTO<CustomerDTO>>> getAllPageAndSortHandler(
         @RequestParam(defaultValue = AppConstants.CURRENT_PAGE) Integer currentPage,
         @RequestParam(defaultValue = AppConstants.TOTAL_ITEMS_PER_PAGE) Integer totalItemsPerPage,
         @RequestParam(defaultValue = AppConstants.SORT_BY) String sortBy,
         @RequestParam(defaultValue = AppConstants.SORT_ORDER) String sortOrder,
         @RequestParam(defaultValue = AppConstants.STATUS) String status) {
      ServiceDTO<Page<CustomerDTO>> result = customerService.getAllPageAndSort(
            currentPage,
            totalItemsPerPage,
            sortBy,
            sortOrder,
            status);
      return ResponseHandler.getResponsePage(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @PostMapping
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> createHandler(
         @RequestBody CustomerDTO customerDTO) throws IOException {

      ServiceDTO<CustomerDTO> result = customerService.create(customerDTO);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @PutMapping("/{id}")
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> updateHandler(
         @PathVariable UUID id,
         @RequestBody CustomerDTO customerDTO) throws IOException {
      ServiceDTO<CustomerDTO> result = customerService.update(id, customerDTO);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> softDeleteHandler(@PathVariable UUID id) throws IOException {
      ServiceDTO<CustomerDTO> result = customerService.softDelete(id);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @DeleteMapping("/destroy/{id}")
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> hardDeleteHandler(@PathVariable UUID id) throws IOException {
      ServiceDTO<CustomerDTO> result = customerService.hardDelete(id);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @PutMapping("/recover/{id}")
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> recoverHandler(@PathVariable UUID id) throws IOException {
      ServiceDTO<CustomerDTO> result = customerService.recover(id);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

}
