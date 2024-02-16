package com.gmf.engineshop.assistant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import com.gmf.engineshop.assistant.core.helper.ClassIdentifier;
import com.gmf.engineshop.assistant.module.customer.dto.CustomerDTO;
import com.gmf.engineshop.assistant.module.engine.dto.EngineDTO;
import com.gmf.engineshop.assistant.module.engineavailability.dto.EngineAvailabilityDTO;
import com.gmf.engineshop.assistant.module.enginefamily.dto.EngineFamilyDTO;
import com.gmf.engineshop.assistant.module.enginemodel.dto.EngineModelDTO;

public class ClassIdentifierTest {

   @Test
   public void mustReturnCustomerName() {
      String name = new ClassIdentifier<CustomerDTO>(new CustomerDTO().getClass()).getName();
      assertEquals("customer", name);
      assertNotEquals("Customer", name);
   }

   @Test
   public void mustReturnEngineFamilyName() {
      String name = new ClassIdentifier<EngineFamilyDTO>(new EngineFamilyDTO().getClass()).getName();
      assertEquals("engine_family", name);
      assertNotEquals("Engine_family", name);
   }

   @Test
   public void mustReturnEngineModelName() {
      String name = new ClassIdentifier<EngineModelDTO>(new EngineModelDTO().getClass()).getName();
      assertEquals("engine_model", name);
      assertNotEquals("Engine_model", name);
   }

   @Test
   public void mustReturnEngineName() {
      String name = new ClassIdentifier<EngineDTO>(new EngineDTO().getClass()).getName();
      assertEquals("engine", name);
      assertNotEquals("Engine", name);
   }

   @Test
   public void mustReturnEngineAvailabilityName() {
      String name = new ClassIdentifier<EngineAvailabilityDTO>(new EngineAvailabilityDTO().getClass()).getName();
      assertEquals("engine_availability", name);
      assertNotEquals("Engine_availability", name);
   }

}
