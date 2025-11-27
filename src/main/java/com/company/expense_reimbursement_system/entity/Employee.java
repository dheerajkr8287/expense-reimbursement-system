package com.company.expense_reimbursement_system.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false ,length = 100)
    private String name;
    @Column(nullable = false,length = 50)
    private String department;
    @Column(nullable = false,unique = true,length = 100)
    private  String email;

}
