package com.gmf.engineshop.assistant.core.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class HttpResponseDTO<T> {
   private T body;
   private String message;
   private String meta;

   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss a", timezone = "UTC")
   private Date time;
}
