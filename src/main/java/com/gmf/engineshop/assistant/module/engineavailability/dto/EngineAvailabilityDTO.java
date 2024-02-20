package com.gmf.engineshop.assistant.module.engineavailability.dto;

import java.util.Date;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.gmf.engineshop.assistant.core.model.BaseDTO;
import com.gmf.engineshop.assistant.module.engine.dto.EngineDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Table(name = "engine_availability")
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EngineAvailabilityDTO extends BaseDTO<EngineAvailabilityDTO> {

   @ManyToOne(fetch = FetchType.EAGER, optional = false)
   @JoinColumn(name = "engine_id", nullable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JsonIncludeProperties({ "id", "esn" })
   private EngineDTO engine;

   @Builder.Default
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
   private Date datePerformed = new Date();

   @Builder.Default
   private Boolean isIncoming = Boolean.TRUE;
}
