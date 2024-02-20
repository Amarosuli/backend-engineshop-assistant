package com.gmf.engineshop.assistant.core.helper;

import java.lang.reflect.Field;

import com.gmf.engineshop.assistant.module.enginemodel.dto.EngineModelDTO;
import com.gmf.engineshop.assistant.module.enginemodel.dto.EngineModelRequestDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * This is a mapper method for handling transformation data.
 * The scenario is get the target as field reference for mapping to its type.
 * The scenario is because in creating entity using the inheritance.
 * And that's make some properties cannot accessible by getDeclaredFields.
 */
@Getter
@Setter
public class DTOMapper<T, S> {
   private T from;
   private S to;

   public DTOMapper(T from, S to) {
      this.setFrom(from);
      this.setTo(to);
   }

   public void fromDTO(T from) {
      this.setFrom(from);
   }

   public S toDTO() {
      Field[] fields = to.getClass().getDeclaredFields();
      for (Field field : fields) {
         try {
            field.setAccessible(true);
            Object value = field.get(this.from);
            if (value != null) {
               Field targetField = to.getClass().getDeclaredField(field.getName());
               targetField.setAccessible(true);
               targetField.set(this.to, value);
            }
         } catch (IllegalAccessException | NoSuchFieldException | SecurityException e) {
            throw new RuntimeException("Error copying fields", e);
         }
      }

      return this.to;
   }

   public EngineModelDTO engineModelRequestDTOtoengineModelDTO(EngineModelRequestDTO engineModelRequestDTO) {

      return EngineModelDTO
            .builder()
            .name(engineModelRequestDTO.getName())
            .description(engineModelRequestDTO.getDescription())
            .build();
   }
}
