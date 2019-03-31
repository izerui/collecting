package com.github.collecting.dao;


import com.github.collecting.entity.Customer;
import com.github.collecting.jpa.PlatformJpaRepository;

public interface CustomerDao extends PlatformJpaRepository<Customer, Long> {
}
