package com.gmf.engineshop.assistant.module.customer;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmf.engineshop.assistant.module.customer.dto.CustomerDTO;

public interface CustomerRepository<T extends CustomerDTO> extends JpaRepository<T, UUID> {

}
