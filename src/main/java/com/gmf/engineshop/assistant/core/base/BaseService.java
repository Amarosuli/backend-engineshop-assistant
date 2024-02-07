package com.gmf.engineshop.assistant.core.base;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import lombok.NonNull;

/**
 * BaseService will be a parent of other Services.
 * Since it have common methods that will be used by all Services.
 */
public interface BaseService<T> {

   ServiceDTO<List<T>> getAll(String status);

   ServiceDTO<T> getById(@NonNull UUID id);

   ServiceDTO<Page<T>> getAllPageAndSort(
         Integer currentPage,
         Integer totalItemsPerPage,
         String sortBy,
         String sortOrder,
         String status);

   ServiceDTO<T> create(T request) throws IOException;

   ServiceDTO<T> update(@NonNull UUID id, T request) throws IOException;

   ServiceDTO<T> softDelete(@NonNull UUID id) throws IOException;

   ServiceDTO<T> hardDelete(@NonNull UUID id) throws IOException;

   ServiceDTO<T> recover(@NonNull UUID id) throws IOException;

}
