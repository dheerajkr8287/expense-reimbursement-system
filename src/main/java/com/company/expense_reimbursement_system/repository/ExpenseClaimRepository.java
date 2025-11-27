package com.company.expense_reimbursement_system.repository;

import com.company.expense_reimbursement_system.entity.ExpenseClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseClaimRepository  extends JpaRepository<ExpenseClaim, Long> {

    List<ExpenseClaim> findByEmployeeId(Long employeeId);


    @Query("SELECT ec FROM ExpenseClaim ec JOIN FETCH ec.employee"+
            " WHERE ec.dateSubmitted BETWEEN :startDate AND :endDate "+
              "ORDER BY ec.dateSubmitted DESC")
    List<ExpenseClaim> findClaimsByDateRange(
            @Param("startDate")LocalDate startDate,
            @Param("endDate") LocalDate endDate

            );
}
