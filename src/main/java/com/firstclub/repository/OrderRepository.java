package com.firstclub.repository;

import com.firstclub.entity.Order;
import com.firstclub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository
        extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);

}