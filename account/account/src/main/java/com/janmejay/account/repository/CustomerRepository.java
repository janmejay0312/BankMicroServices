package com.janmejay.account.repository;

import com.janmejay.account.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Long> {

    @Query
    public Optional<Customer> findByMobileNumber(@Param("mobileNo") String mobileNo);
}
