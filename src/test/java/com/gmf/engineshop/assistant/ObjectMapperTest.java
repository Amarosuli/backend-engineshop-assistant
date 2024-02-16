package com.gmf.engineshop.assistant;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.gmf.engineshop.assistant.core.helper.ObjectMapper;
import com.gmf.engineshop.assistant.module.enginefamily.dto.EngineFamilyDTO;
import com.gmf.engineshop.assistant.module.enginemodel.dto.EngineModelDTO;

public class ObjectMapperTest {
   @Test
   public void mustReturnTargetDTOClass() {
      // given
      UUID id = UUID.randomUUID();

      EngineModelDTO target = new EngineModelDTO();
      target.setEngineFamily(EngineFamilyDTO.builder().id(id).build());

      EngineModelDTO expect = EngineModelDTO
            .builder()
            .name("CFM56-3")
            .description("Third generation of CFM56 engine series")
            .engineFamily(EngineFamilyDTO.builder().id(id).build())
            .build();

      // when
      ObjectMapper.map(expect, target);

      // then
      assertEquals(expect, target);
   }
}
