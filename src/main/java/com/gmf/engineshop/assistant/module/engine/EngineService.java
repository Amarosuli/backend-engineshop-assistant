package com.gmf.engineshop.assistant.module.engine;

import java.io.IOException;

import com.gmf.engineshop.assistant.core.model.BaseService;
import com.gmf.engineshop.assistant.core.model.ServiceDTO;
import com.gmf.engineshop.assistant.module.engine.dto.EngineDTO;
import com.gmf.engineshop.assistant.module.engine.dto.EngineRequestDTO;

public interface EngineService extends BaseService<EngineDTO> {
   ServiceDTO<EngineDTO> create(EngineRequestDTO request) throws IOException;
}
