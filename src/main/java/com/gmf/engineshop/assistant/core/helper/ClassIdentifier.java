package com.gmf.engineshop.assistant.core.helper;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClassIdentifier<T> {
   private final Class<?> entityClass;

   public String getName() {
      return entityClass.getAnnotation(Table.class).name();
   }
}
