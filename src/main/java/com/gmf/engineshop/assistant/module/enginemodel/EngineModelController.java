package com.gmf.engineshop.assistant.module.enginemodel;

import org.springframework.web.bind.annotation.RestController;

import com.gmf.engineshop.assistant.core.model.HttpResponseDTO;
import com.gmf.engineshop.assistant.core.model.PageAndSortResultDTO;
import com.gmf.engineshop.assistant.core.model.ServiceDTO;
import com.gmf.engineshop.assistant.core.utility.AppConstants;
import com.gmf.engineshop.assistant.core.utility.ResponseHandler;
import com.gmf.engineshop.assistant.module.enginefamily.EngineFamilyService;
import com.gmf.engineshop.assistant.module.enginefamily.dto.EngineFamilyDTO;
import com.gmf.engineshop.assistant.module.enginemodel.dto.EngineModelDTO;
import com.gmf.engineshop.assistant.module.enginemodel.dto.EngineModelRequestDTO;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/engine_model")
public class EngineModelController {

   @Autowired
   private EngineModelService engineModelService;

   @Autowired
   private EngineFamilyService engineFamilyService;

   @GetMapping("/{id}")
   public ResponseEntity<HttpResponseDTO<EngineModelDTO>> getByIdHandler(@PathVariable UUID id) {
      ServiceDTO<EngineModelDTO> result = engineModelService.getById(id);

      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @GetMapping
   public ResponseEntity<HttpResponseDTO<PageAndSortResultDTO<EngineModelDTO>>> getAllPageAndSortHandler(
         @RequestParam(defaultValue = AppConstants.CURRENT_PAGE) Integer currentPage,
         @RequestParam(defaultValue = AppConstants.TOTAL_ITEMS_PER_PAGE) Integer totalItemsPerPage,
         @RequestParam(defaultValue = AppConstants.SORT_BY) String sortBy,
         @RequestParam(defaultValue = AppConstants.SORT_ORDER) String sortOrder,
         @RequestParam(defaultValue = AppConstants.STATUS) String status) {
      ServiceDTO<Page<EngineModelDTO>> result = engineModelService.getAllPageAndSort(
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
   public ResponseEntity<HttpResponseDTO<EngineModelDTO>> createHandler(
         @RequestBody EngineModelRequestDTO engineModelRequestDTO)
         throws IOException {

      EngineFamilyDTO engineFamily = engineFamilyService.getById(engineModelRequestDTO.getEngineFamilyId()).getData();

      EngineModelDTO engineModelDTO = EngineModelDTO
            .builder()
            .name(engineModelRequestDTO.getName())
            .description(engineModelRequestDTO.getDescription())
            .engineFamily(engineFamily)
            .build();

      ServiceDTO<EngineModelDTO> result = engineModelService.create(engineModelDTO);

      EngineModelDTO finalR = EngineModelDTO
            .builder()
            .id(result.getData().getId())
            .name(result.getData().getName())
            .description(result.getData().getDescription())
            .engineFamily(engineFamily)
            .created(result.getData().getCreated())
            .deleted(result.getData().getDeleted())
            .updated(result.getData().getUpdated())
            .build();

      return ResponseHandler.getResponseEntity(
            finalR,
            result.getMessage(),
            result.getStatus());
   }

   @PutMapping("/{id}")
   public ResponseEntity<HttpResponseDTO<EngineModelDTO>> updateHandler(
         @PathVariable UUID id,
         @RequestBody EngineModelDTO engineModelDTO)
         throws IOException {
      ServiceDTO<EngineModelDTO> result = engineModelService.update(id, engineModelDTO);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<HttpResponseDTO<EngineModelDTO>> softDeleteHandler(@PathVariable UUID id) throws IOException {
      ServiceDTO<EngineModelDTO> result = engineModelService.softDelete(id);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @DeleteMapping("/destroy/{id}")
   public ResponseEntity<HttpResponseDTO<EngineModelDTO>> hardDeleteHandler(@PathVariable UUID id) throws IOException {
      ServiceDTO<EngineModelDTO> result = engineModelService.hardDelete(id);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

   @PutMapping("/recover/{id}")
   public ResponseEntity<HttpResponseDTO<EngineModelDTO>> recoverHandler(@PathVariable UUID id) throws IOException {
      ServiceDTO<EngineModelDTO> result = engineModelService.recover(id);
      return ResponseHandler.getResponseEntity(
            result.getData(),
            result.getMessage(),
            result.getStatus());
   }

}
