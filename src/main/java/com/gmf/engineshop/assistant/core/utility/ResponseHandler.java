package com.gmf.engineshop.assistant.core.utility;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gmf.engineshop.assistant.core.base.*;
import com.gmf.engineshop.assistant.core.helper.ClassIdentifier;

public class ResponseHandler<T> {

   @SuppressWarnings("null")
   public static <T> ResponseEntity<HttpResponseDTO<ResultDTO<T>>> getResponseListEntity(List<T> responseObject,
         String message,
         HttpStatus status) {
      HttpResponseDTO<ResultDTO<T>> httpResponseDTO = new HttpResponseDTO<>();
      ResultDTO<T> resultDTO = new ResultDTO<>();

      resultDTO.setData(responseObject);
      httpResponseDTO.setBody(resultDTO);
      httpResponseDTO.setMessage(message);
      httpResponseDTO.setTime(new Date());
      httpResponseDTO.setMeta("meta mete");

      return new ResponseEntity<>(httpResponseDTO, status);
   }

   @SuppressWarnings("null")
   public static <T> ResponseEntity<HttpResponseDTO<T>> getResponseEntity(T responseObject, String message,
         HttpStatus status) {
      HttpResponseDTO<T> httpResponseDTO = new HttpResponseDTO<>();
      httpResponseDTO.setBody(responseObject);
      httpResponseDTO.setMessage(message);
      httpResponseDTO.setTime(new Date());
      httpResponseDTO.setMeta("meta mete");

      return new ResponseEntity<>(httpResponseDTO, status);
   }

   @SuppressWarnings("null")
   public static <T> ResponseEntity<HttpResponseDTO<PageAndSortResultDTO<T>>> getResponsePage(
         Page<T> responseObject,
         String message,
         HttpStatus status) {

      PageAndSortResultDTO<T> pageObject = new PageAndSortResultDTO<>();
      List<T> data = responseObject.getContent();
      String entityName = new ClassIdentifier<T>(data.get(0).getClass()).getName();

      pageObject.setCurrentPage(responseObject.getNumber());
      pageObject.setData(data);
      pageObject.setTotalItems(responseObject.getSize());
      pageObject.setTotalItemsPerPage(responseObject.getNumberOfElements());
      pageObject.setLastPage(responseObject.isLast());
      pageObject.setHasNext(responseObject.hasNext());
      pageObject.setHasPrev(responseObject.hasPrevious());
      pageObject.setSortBy(entityName + "." + responseObject.getSort().toString());
      pageObject.setSortOrder(responseObject.getSort().toString().split(" ")[1]);

      HttpResponseDTO<PageAndSortResultDTO<T>> httpResponseDTO = new HttpResponseDTO<>();
      httpResponseDTO.setBody(pageObject);
      httpResponseDTO.setMessage(message);
      httpResponseDTO.setTime(new Date());
      httpResponseDTO.setMeta("meta mete");

      return new ResponseEntity<>(httpResponseDTO, status);
   }
}
