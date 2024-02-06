package com.gmf.engineshop.assistant.core.base;

import java.io.IOException;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import lombok.NonNull;

/**
 * BaseService will be a parent of other Services.
 * Since it have common methods that will be used by all Services.
 */
public interface BaseService<T> {

   ResponseEntity<HttpResponseDTO<ResultDTO<T>>> getAll();

   ResponseEntity<HttpResponseDTO<T>> getById(@NonNull UUID id);

   ResponseEntity<HttpResponseDTO<PageAndSortResultDTO<T>>> getAllPageAndSort(Integer currentPage,
         Integer totalItemsPerPage, String sortBy, String sortOrder);

   ResponseEntity<HttpResponseDTO<T>> create(T request) throws IOException;

   ResponseEntity<HttpResponseDTO<T>> update(@NonNull UUID id, T request) throws IOException;

   ResponseEntity<HttpResponseDTO<T>> softDelete(@NonNull UUID id) throws IOException;

   ResponseEntity<HttpResponseDTO<T>> hardDelete(@NonNull UUID id) throws IOException;

   ResponseEntity<HttpResponseDTO<T>> recover(@NonNull UUID id) throws IOException;

}
