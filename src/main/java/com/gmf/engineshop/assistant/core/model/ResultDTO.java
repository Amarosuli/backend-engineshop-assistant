package com.gmf.engineshop.assistant.core.model;

import java.util.List;

import lombok.Data;

@Data
public class ResultDTO<T> {
   private List<T> data;
}
