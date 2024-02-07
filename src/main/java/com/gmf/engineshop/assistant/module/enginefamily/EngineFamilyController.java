package com.gmf.engineshop.assistant.module.enginefamily;

import java.io.IOException;
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
import com.gmf.engineshop.assistant.core.model.ServiceDTO;
import com.gmf.engineshop.assistant.core.utility.AppConstants;
import com.gmf.engineshop.assistant.core.utility.ResponseHandler;
import com.gmf.engineshop.assistant.module.enginefamily.dto.EngineFamilyDTO;

@RestController
@RequestMapping("/api/v1/engine_family")
public class EngineFamilyController {

   @Autowired
   private EngineFamilyService engineFamilyService;

   @GetMapping("/{id}")
   public ResponseEntity<HttpResponseDTO<EngineFamilyDTO>> getByIdHandler(@PathVariable UUID id) {
      ServiceDTO<EngineFamilyDTO> result = engineFamilyService.getById(id);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @GetMapping
   public ResponseEntity<HttpResponseDTO<PageAndSortResultDTO<EngineFamilyDTO>>> getAllPageAndSortHandler(
         @RequestParam(defaultValue = AppConstants.CURRENT_PAGE) Integer currentPage,
         @RequestParam(defaultValue = AppConstants.TOTAL_ITEMS_PER_PAGE) Integer totalItemsPerPage,
         @RequestParam(defaultValue = AppConstants.SORT_BY) String sortBy,
         @RequestParam(defaultValue = AppConstants.SORT_ORDER) String sortOrder,
         @RequestParam(defaultValue = AppConstants.STATUS) String status) {
      ServiceDTO<Page<EngineFamilyDTO>> result = engineFamilyService.getAllPageAndSort(
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
   public ResponseEntity<HttpResponseDTO<EngineFamilyDTO>> createHandler(
         @RequestBody EngineFamilyDTO engineFamilyDTO) throws IOException {
      ServiceDTO<EngineFamilyDTO> result = engineFamilyService.create(engineFamilyDTO);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @PutMapping("/{id}")
   public ResponseEntity<HttpResponseDTO<EngineFamilyDTO>> updateHandler(
         @PathVariable UUID id,
         @RequestBody EngineFamilyDTO engineFamilyDTO) throws IOException {
      ServiceDTO<EngineFamilyDTO> result = engineFamilyService.update(id, engineFamilyDTO);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<HttpResponseDTO<EngineFamilyDTO>> softDeleteHandler(@PathVariable UUID id) throws IOException {
      ServiceDTO<EngineFamilyDTO> result = engineFamilyService.softDelete(id);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @DeleteMapping("/destroy/{id}")
   public ResponseEntity<HttpResponseDTO<EngineFamilyDTO>> hardDeleteHandler(@PathVariable UUID id) throws IOException {
      ServiceDTO<EngineFamilyDTO> result = engineFamilyService.hardDelete(id);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @PutMapping("/recover/{id}")
   public ResponseEntity<HttpResponseDTO<EngineFamilyDTO>> recoverHandler(@PathVariable UUID id) throws IOException {
      ServiceDTO<EngineFamilyDTO> result = engineFamilyService.recover(id);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

}
