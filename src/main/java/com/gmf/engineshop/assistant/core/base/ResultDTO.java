package com.gmf.engineshop.assistant.core.base;

import java.util.List;

import lombok.Data;

@Data
public class ResultDTO<T> {
   private List<T> data;
}
