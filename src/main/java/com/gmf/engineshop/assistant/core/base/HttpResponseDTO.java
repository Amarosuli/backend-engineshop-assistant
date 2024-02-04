package com.gmf.engineshop.assistant.core.base;

import java.util.Date;

import lombok.Data;

@Data
public class HttpResponseDTO<T> {
   private T body;
   private String message;
   private String meta;
   private Date time;
}
