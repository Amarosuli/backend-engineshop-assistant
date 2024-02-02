package com.gmf.engineshop.assistant.core.base;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;

public interface BaseService<T, CR, UR, R> {

   ResponseEntity<R> getAll() throws HttpStatusCodeException;

   ResponseEntity<R> getById(UUID id) throws HttpStatusCodeException;

   ResponseEntity<R> query(String field, String param) throws HttpStatusCodeException;

   ResponseEntity<R> create(CR request) throws HttpStatusCodeException;

   ResponseEntity<R> update(UUID id, UR request) throws HttpStatusCodeException;

   ResponseEntity<R> softDelete(UUID id) throws HttpStatusCodeException;

   ResponseEntity<R> hardDelete(UUID id) throws HttpStatusCodeException;

   ResponseEntity<R> recover(UUID id) throws HttpStatusCodeException;

}
