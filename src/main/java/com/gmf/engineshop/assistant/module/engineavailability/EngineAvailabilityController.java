package com.gmf.engineshop.assistant.module.engineavailability;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmf.engineshop.assistant.core.model.HttpResponseDTO;
import com.gmf.engineshop.assistant.core.model.PageAndSortResultDTO;
import com.gmf.engineshop.assistant.core.model.ServiceDTO;
import com.gmf.engineshop.assistant.core.utility.AppConstants;
import com.gmf.engineshop.assistant.core.utility.ResponseHandler;
import com.gmf.engineshop.assistant.module.engineavailability.dto.EngineAvailabilityDTO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/v1/engine_availability")
public class EngineAvailabilityController {

   @Autowired
   private EngineAvailabilityService engineAvailabilityService;

   @GetMapping("/{id}")
   public ResponseEntity<HttpResponseDTO<EngineAvailabilityDTO>> getByIdHandler(@PathVariable UUID id) {
      ServiceDTO<EngineAvailabilityDTO> result = engineAvailabilityService.getById(id);

      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @GetMapping
   public ResponseEntity<HttpResponseDTO<PageAndSortResultDTO<EngineAvailabilityDTO>>> getAllPageAndSortHandler(
         @RequestParam(defaultValue = AppConstants.CURRENT_PAGE) Integer currentPage,
         @RequestParam(defaultValue = AppConstants.TOTAL_ITEMS_PER_PAGE) Integer totalItemsPerPage,
         @RequestParam(defaultValue = AppConstants.SORT_BY) String sortBy,
         @RequestParam(defaultValue = AppConstants.SORT_ORDER) String sortOrder,
         @RequestParam(defaultValue = AppConstants.STATUS) String status) {
      ServiceDTO<Page<EngineAvailabilityDTO>> result = engineAvailabilityService.getAllPageAndSort(
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

   @GetMapping("/history/{engineId}")
   public ResponseEntity<HttpResponseDTO<PageAndSortResultDTO<EngineAvailabilityDTO>>> getEngineHistoryHandler(
         @PathVariable UUID engineId,
         @RequestParam(defaultValue = AppConstants.CURRENT_PAGE) Integer currentPage,
         @RequestParam(defaultValue = AppConstants.TOTAL_ITEMS_PER_PAGE) Integer totalItemsPerPage,
         @RequestParam(defaultValue = AppConstants.SORT_BY) String sortBy,
         @RequestParam(defaultValue = AppConstants.SORT_ORDER) String sortOrder,
         @RequestParam(defaultValue = AppConstants.STATUS) String status) {
      ServiceDTO<Page<EngineAvailabilityDTO>> result = engineAvailabilityService.getEngineHistory(
            engineId,
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

   @PostMapping("/outgoing/{engineId}")
   public ResponseEntity<HttpResponseDTO<EngineAvailabilityDTO>> createEngineOutgoingHandler(
         @PathVariable UUID engineId)
         throws IOException {

      ServiceDTO<EngineAvailabilityDTO> result = engineAvailabilityService.createEngineOutgoing(engineId);

      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @PostMapping("/incoming/{engineId}")
   public ResponseEntity<HttpResponseDTO<EngineAvailabilityDTO>> createEngineIncomingHandler(
         @PathVariable UUID engineId)
         throws IOException {

      ServiceDTO<EngineAvailabilityDTO> result = engineAvailabilityService.createEngineIncoming(engineId);

      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

}
