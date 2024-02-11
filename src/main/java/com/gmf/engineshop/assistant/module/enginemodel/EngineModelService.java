package com.gmf.engineshop.assistant.module.enginemodel;

import java.io.IOException;

import com.gmf.engineshop.assistant.core.model.BaseService;
import com.gmf.engineshop.assistant.core.model.ServiceDTO;
import com.gmf.engineshop.assistant.module.enginemodel.dto.EngineModelDTO;
import com.gmf.engineshop.assistant.module.enginemodel.dto.EngineModelRequestDTO;

public interface EngineModelService extends BaseService<EngineModelDTO> {
   ServiceDTO<EngineModelDTO> create(EngineModelRequestDTO request) throws IOException;
}
