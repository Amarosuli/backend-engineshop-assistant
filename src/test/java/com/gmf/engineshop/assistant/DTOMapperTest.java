package com.gmf.engineshop.assistant;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.gmf.engineshop.assistant.core.helper.DTOMapper;
import com.gmf.engineshop.assistant.module.enginefamily.dto.EngineFamilyDTO;
import com.gmf.engineshop.assistant.module.enginemodel.dto.EngineModelDTO;
import com.gmf.engineshop.assistant.module.enginemodel.dto.EngineModelRequestDTO;

public class DTOMapperTest {

   @Test
   public void mustReturnTargetDTOClass() {
      // given
      UUID id = UUID.randomUUID();
      EngineModelRequestDTO from = EngineModelRequestDTO
            .builder()
            .name("CFM56-3")
            .description("Third generation of CFM56 engine series")
            .engineFamilyId(id)
            .build();

      EngineModelDTO target = new EngineModelDTO();
      target.setEngineFamily(EngineFamilyDTO.builder().id(id).build());

      EngineModelDTO expect = EngineModelDTO
            .builder()
            .name("CFM56-3")
            .description("Third generation of CFM56 engine series")
            .engineFamily(EngineFamilyDTO.builder().id(id).build())
            .build();

      // when
      DTOMapper<EngineModelRequestDTO, EngineModelDTO> mapper = new DTOMapper<>(from, target);
      EngineModelDTO result = mapper.toDTO();

      for (Field c : from.getClass().getFields()) {
         System.out.println(c.getName());
         // for (Class field : c.getClass().getClasses()) {
         // }
      }
      // then
      assertEquals(expect, result);

   }
}
