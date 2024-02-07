package com.gmf.engineshop.assistant.core.model;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * BaseDTO will be parent of all DTOs.
 * It represent the id, deleted, created, and updated fields.
 * Since the table will always have those four fields.
 */
@Data
@SuperBuilder
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BaseDTO<T extends BaseDTO<T>> {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @Builder.Default
   private Boolean deleted = Boolean.FALSE;

   @CreationTimestamp(source = SourceType.DB)
   private Instant created;

   @UpdateTimestamp(source = SourceType.DB)
   private Instant updated;
}
