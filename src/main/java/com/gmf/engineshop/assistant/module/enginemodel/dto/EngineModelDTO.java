package com.gmf.engineshop.assistant.module.enginemodel.dto;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.gmf.engineshop.assistant.core.model.BaseDTO;
import com.gmf.engineshop.assistant.module.enginefamily.dto.EngineFamilyDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Table(name = "engine_model")
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EngineModelDTO extends BaseDTO<EngineModelDTO> {

   @Column(unique = true, nullable = false)
   private String name;
   private String description;
   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name = "engine_family_id", nullable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JsonIncludeProperties({ "id", "name" })
   private EngineFamilyDTO engineFamily;
}
