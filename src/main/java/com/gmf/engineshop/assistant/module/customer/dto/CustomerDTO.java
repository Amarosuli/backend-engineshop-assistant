package com.gmf.engineshop.assistant.module.customer.dto;

import com.gmf.engineshop.assistant.core.base.BaseDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Table(name = "customer")
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerDTO extends BaseDTO<CustomerDTO> {

   @Column(unique = true)
   private String name;
   private String description;
   private String alias;
   private String email;
   private String address;
}
