package com.janmejay.account.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Customer")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "customer_name")
    private String name;

    @Column(name = "customer_email")
    private String email;

    @Column(name = "customer_mobile_no")
    private String mobileNumber;
}
