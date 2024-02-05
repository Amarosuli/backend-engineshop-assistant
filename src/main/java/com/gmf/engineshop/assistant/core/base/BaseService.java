package com.gmf.engineshop.assistant.core.base;

import java.io.IOException;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

public interface BaseService<T> {

   ResponseEntity<HttpResponseDTO<ResultDTO<T>>> getAll();

   ResponseEntity<HttpResponseDTO<T>> getById(UUID id);

   ResponseEntity<Object> query(String field, String param);

   ResponseEntity<HttpResponseDTO<T>> create(T request) throws IOException;

   ResponseEntity<HttpResponseDTO<T>> update(UUID id, T request) throws IOException;

   ResponseEntity<HttpResponseDTO<T>> softDelete(UUID id) throws IOException;

   ResponseEntity<HttpResponseDTO<T>> hardDelete(UUID id) throws IOException;

   ResponseEntity<HttpResponseDTO<T>> recover(UUID id) throws IOException;

}
