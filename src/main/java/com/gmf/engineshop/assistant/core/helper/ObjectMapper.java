package com.gmf.engineshop.assistant.core.helper;

import java.lang.reflect.Field;

/**
 * This is a mapper method for handling request data to original DTO data.
 * Used by Service.update
 */
public class ObjectMapper {
   public static <S, T> void map(S sourceData, T target) {
      Field[] fields = sourceData.getClass().getDeclaredFields();
      for (Field field : fields) {
         try {
            field.setAccessible(true);
            Object value = field.get(sourceData);
            if (value != null) {
               Field targetField = target.getClass().getDeclaredField(field.getName());
               targetField.setAccessible(true);
               targetField.set(target, value);
            }
         } catch (IllegalAccessException | NoSuchFieldException | SecurityException e) {
            throw new RuntimeException("Error copying fields", e);
         }
      }
   }
}
