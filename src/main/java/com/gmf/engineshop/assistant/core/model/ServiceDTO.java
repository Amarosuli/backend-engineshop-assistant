package com.gmf.engineshop.assistant.core.model;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO<T> {
   private T data;
   private String message;
   private HttpStatus status;
}
