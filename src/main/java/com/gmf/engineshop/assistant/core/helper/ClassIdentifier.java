package com.gmf.engineshop.assistant.core.helper;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

/**
 * This is a simple class method to get name of entity / dto class
 * Entity name which define by @Table(name = ...) from lombok.
 * Used by ResponseHandler.getResponsePage
 */
@AllArgsConstructor
public class ClassIdentifier<T> {
   private final Class<?> entityClass;

   public String getName() {
      return entityClass.getAnnotation(Table.class).name();
   }
}
