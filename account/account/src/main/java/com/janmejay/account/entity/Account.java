package com.janmejay.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends BaseEntity{


    @Column(name = "customer_id")
    private Long customerId;

    @Id
    @Column(name = "account_id")
    private Long accountNo;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "branch_address")
    private String branchAddress;
}

