package com.gmf.engineshop.assistant.module.customer;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.gmf.engineshop.assistant.core.base.HttpResponseDTO;
import com.gmf.engineshop.assistant.core.base.PageAndSortResultDTO;
import com.gmf.engineshop.assistant.core.base.ResultDTO;
import com.gmf.engineshop.assistant.core.utility.AppConstants;
import com.gmf.engineshop.assistant.module.customer.dto.CustomerDTO;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

   @Autowired
   private CustomerService customerService;

   @GetMapping
   public ResponseEntity<HttpResponseDTO<ResultDTO<CustomerDTO>>> getAllHandler() {
      return customerService.getAll();
   }

   @GetMapping("/{id}")
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> getByIdHandler(@PathVariable UUID id) {
      return customerService.getById(id);
   }

   @GetMapping("/pagination")
   public ResponseEntity<HttpResponseDTO<PageAndSortResultDTO<CustomerDTO>>> getAllPageAndSortHandler(
         @RequestParam(defaultValue = AppConstants.CURRENT_PAGE) Integer currentPage,
         @RequestParam(defaultValue = AppConstants.TOTAL_ITEMS_PER_PAGE) Integer totalItemsPerPage,
         @RequestParam(defaultValue = AppConstants.SORT_BY) String sortBy,
         @RequestParam(defaultValue = AppConstants.SORT_ORDER) String sortOrder) {
      return customerService.getAllPageAndSort(currentPage, totalItemsPerPage, sortBy, sortOrder);
   }

   @PostMapping
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> createHandler(@RequestBody CustomerDTO customerDTO)
         throws IOException {
      return customerService.create(customerDTO);
   }

   @PutMapping("/{id}")
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> updateHandler(@PathVariable UUID id,
         @RequestBody CustomerDTO customerDTO) throws IOException {
      return customerService.update(id, customerDTO);
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> softDeleteHandler(@PathVariable UUID id) throws IOException {
      return customerService.softDelete(id);
   }

   @DeleteMapping("/destroy/{id}")
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> hardDeleteHandler(@PathVariable UUID id) throws IOException {
      return customerService.hardDelete(id);
   }

   @PutMapping("/recover/{id}")
   public ResponseEntity<HttpResponseDTO<CustomerDTO>> recoverHandler(@PathVariable UUID id) throws IOException {
      return customerService.recover(id);
   }

}
