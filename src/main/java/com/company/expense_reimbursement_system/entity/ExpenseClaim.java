package com.company.expense_reimbursement_system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expense_claims" )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseClaim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "employee_id" ,nullable = false)
    private Employee employee;

    @Column(name="date_submitted" ,nullable = false)
    private LocalDate dateSubmitted;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)  //is used to tell JPA how to store an enum in the database.
                                  // Store the enum value as text (STRING) in the database, not as a number.
    @Column(nullable = false,length = 20)
    private  ClaimStatus status;

    public  enum  ClaimStatus{
        Pending,
        Approved,
        Rejected
    }



}

