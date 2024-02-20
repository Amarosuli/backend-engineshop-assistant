package com.gmf.engineshop.assistant;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.gmf.engineshop.assistant.module.engine.dto.EngineDTO;
import com.gmf.engineshop.assistant.module.engineavailability.dto.EngineAvailabilityDTO;

public class DTOTests {

   @Test
   public void mustGenerateExpectedEngineAvailability() {
      EngineAvailabilityDTO engineAvailability = EngineAvailabilityDTO
            .builder()
            .engine(new EngineDTO())
            .isIncoming(Boolean.TRUE)
            .build();

      EngineAvailabilityDTO expect = new EngineAvailabilityDTO();
      expect.setEngine(new EngineDTO());
      expect.setIsIncoming(Boolean.TRUE);

      assertEquals(expect.getIsIncoming(), engineAvailability.getIsIncoming());
   }

}
