package com.gmf.engineshop.assistant.module.customer;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gmf.engineshop.assistant.module.customer.dto.CustomerDTO;

public interface CustomerRepository<T extends CustomerDTO> extends JpaRepository<T, UUID> {
   List<CustomerDTO> findByDeletedIs(Boolean isDeleted);

   Page<CustomerDTO> findByDeletedIs(Boolean isDeleted, Pageable pageable);
}
