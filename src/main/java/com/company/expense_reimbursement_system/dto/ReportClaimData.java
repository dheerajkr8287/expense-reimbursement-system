package com.company.expense_reimbursement_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportClaimData {
    private String employeeName;
    private String department;
    private String description;
    private BigDecimal amount;
    private String status;
    private LocalDate dateSubmitted;
}
