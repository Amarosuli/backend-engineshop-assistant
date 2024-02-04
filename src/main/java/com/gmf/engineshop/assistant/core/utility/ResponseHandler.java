package com.gmf.engineshop.assistant.core.utility;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gmf.engineshop.assistant.core.base.*;

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
}
