package com.github.collecting.dao;


import com.github.collecting.entity.Order;
import com.github.collecting.jpa.PlatformJpaRepository;

public interface OrderDao extends PlatformJpaRepository<Order,Long> {
}
