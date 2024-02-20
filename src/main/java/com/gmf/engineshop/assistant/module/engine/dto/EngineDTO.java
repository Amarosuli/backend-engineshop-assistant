package com.gmf.engineshop.assistant.module.engine.dto;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.gmf.engineshop.assistant.core.model.BaseDTO;
import com.gmf.engineshop.assistant.module.customer.dto.CustomerDTO;
import com.gmf.engineshop.assistant.module.enginemodel.dto.EngineModelDTO;

import jakarta.persistence.Column;
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
@Table(name = "engine")
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EngineDTO extends BaseDTO<EngineDTO> {

   @Column(unique = true, nullable = false)
   private String esn;

   private String config;

   @Builder.Default
   private Boolean isServiceable = Boolean.FALSE;

   @Builder.Default
   private Boolean isPreservable = Boolean.TRUE;

   private String notes;

   @ManyToOne(fetch = FetchType.EAGER, optional = false)
   @JoinColumn(name = "engine_model_id", nullable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JsonIncludeProperties({ "id", "name" })
   private EngineModelDTO engineModel;

   @ManyToOne(fetch = FetchType.EAGER, optional = false)
   @JoinColumn(name = "customer_id", nullable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JsonIncludeProperties({ "id", "name" })
   private CustomerDTO customer;
}
