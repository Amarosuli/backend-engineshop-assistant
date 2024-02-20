package com.gmf.engineshop.assistant.module.engine;

import org.springframework.web.bind.annotation.RestController;

import com.gmf.engineshop.assistant.core.model.HttpResponseDTO;
import com.gmf.engineshop.assistant.core.model.PageAndSortResultDTO;
import com.gmf.engineshop.assistant.core.model.ServiceDTO;
import com.gmf.engineshop.assistant.core.utility.AppConstants;
import com.gmf.engineshop.assistant.core.utility.ResponseHandler;
import com.gmf.engineshop.assistant.module.engine.dto.EngineDTO;
import com.gmf.engineshop.assistant.module.engine.dto.EngineRequestDTO;
import com.gmf.engineshop.assistant.module.engineavailability.EngineAvailabilityService;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/engine")
public class EngineController {

   @Autowired
   private EngineService engineService;

   @Autowired
   EngineAvailabilityService engineAvailabilityService;

   @GetMapping("/{id}")
   public ResponseEntity<HttpResponseDTO<EngineDTO>> getByIdHandler(@PathVariable UUID id) {
      ServiceDTO<EngineDTO> result = engineService.getById(id);

      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @GetMapping
   public ResponseEntity<HttpResponseDTO<PageAndSortResultDTO<EngineDTO>>> getAllPageAndSortHandler(
         @RequestParam(defaultValue = AppConstants.CURRENT_PAGE) Integer currentPage,
         @RequestParam(defaultValue = AppConstants.TOTAL_ITEMS_PER_PAGE) Integer totalItemsPerPage,
         @RequestParam(defaultValue = AppConstants.SORT_BY) String sortBy,
         @RequestParam(defaultValue = AppConstants.SORT_ORDER) String sortOrder,
         @RequestParam(defaultValue = AppConstants.STATUS) String status) {
      ServiceDTO<Page<EngineDTO>> result = engineService.getAllPageAndSort(
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
   public ResponseEntity<HttpResponseDTO<EngineDTO>> createHandler(
         @RequestBody EngineRequestDTO engineRequestDTO) throws IOException {

      ServiceDTO<EngineDTO> result = engineService.create(engineRequestDTO);

      engineAvailabilityService.createEngineIncoming(result.getData().getId());

      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @PutMapping("/{id}")
   public ResponseEntity<HttpResponseDTO<EngineDTO>> updateHandler(
         @PathVariable UUID id,
         @RequestBody EngineDTO engineDTO) throws IOException {

      ServiceDTO<EngineDTO> result = engineService.update(id, engineDTO);

      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<HttpResponseDTO<EngineDTO>> softDeleteHandler(@PathVariable UUID id) throws IOException {
      ServiceDTO<EngineDTO> result = engineService.softDelete(id);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @DeleteMapping("/destroy/{id}")
   public ResponseEntity<HttpResponseDTO<EngineDTO>> hardDeleteHandler(@PathVariable UUID id) throws IOException {
      ServiceDTO<EngineDTO> result = engineService.hardDelete(id);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @PutMapping("/recover/{id}")
   public ResponseEntity<HttpResponseDTO<EngineDTO>> recoverHandler(@PathVariable UUID id) throws IOException {
      ServiceDTO<EngineDTO> result = engineService.recover(id);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

}
