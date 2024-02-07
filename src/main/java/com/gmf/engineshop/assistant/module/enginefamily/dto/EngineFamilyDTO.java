package com.gmf.engineshop.assistant.module.enginefamily.dto;

import com.gmf.engineshop.assistant.core.model.BaseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Table(name = "engine_family")
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EngineFamilyDTO extends BaseDTO<EngineFamilyDTO> {

   @Column(unique = true, nullable = false)
   private String name;
   private String description;
}
