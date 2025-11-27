package com.company.expense_reimbursement_system.config;

import com.company.expense_reimbursement_system.entity.Employee;
import com.company.expense_reimbursement_system.entity.ExpenseClaim;
import com.company.expense_reimbursement_system.repository.EmployeeRepository;
import com.company.expense_reimbursement_system.repository.ExpenseClaimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader  implements CommandLineRunner {
    @Autowired
    private final EmployeeRepository employeeRepository;
    @Autowired
    private final ExpenseClaimRepository claimRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create sample employees
        Employee emp1 = new Employee();
        emp1.setName("John Doe");
        emp1.setDepartment("Engineering");
        emp1.setEmail("john.doe@company.com");
        employeeRepository.save(emp1);

        Employee emp2 = new Employee();
        emp2.setName("Jane Smith");
        emp2.setDepartment("Marketing");
        emp2.setEmail("jane.smith@company.com");
        employeeRepository.save(emp2);

        Employee emp3 = new Employee();
        emp3.setName("Bob Johnson");
        emp3.setDepartment("Sales");
        emp3.setEmail("bob.johnson@company.com");
        employeeRepository.save(emp3);

        // Create sample expense claims
        ExpenseClaim claim1 = new ExpenseClaim();
        claim1.setEmployee(emp1);
        claim1.setDescription("Office supplies purchase");
        claim1.setAmount(new BigDecimal("150.00"));
        claim1.setDateSubmitted(LocalDate.of(2025, 1, 15));
        claim1.setStatus(ExpenseClaim.ClaimStatus.Approved);
        claimRepository.save(claim1);

        ExpenseClaim claim2 = new ExpenseClaim();
        claim2.setEmployee(emp1);
        claim2.setDescription("Client dinner meeting");
        claim2.setAmount(new BigDecimal("85.50"));
        claim2.setDateSubmitted(LocalDate.of(2025, 1, 20));
        claim2.setStatus(ExpenseClaim.ClaimStatus.Pending);
        claimRepository.save(claim2);

        ExpenseClaim claim3 = new ExpenseClaim();
        claim3.setEmployee(emp2);
        claim3.setDescription("Conference registration fee");
        claim3.setAmount(new BigDecimal("500.00"));
        claim3.setDateSubmitted(LocalDate.of(2025, 1, 10));
        claim3.setStatus(ExpenseClaim.ClaimStatus.Approved);
        claimRepository.save(claim3);

        ExpenseClaim claim4 = new ExpenseClaim();
        claim4.setEmployee(emp2);
        claim4.setDescription("Travel expenses - NYC trip");
        claim4.setAmount(new BigDecimal("1200.00"));
        claim4.setDateSubmitted(LocalDate.of(2025, 1, 25));
        claim4.setStatus(ExpenseClaim.ClaimStatus.Rejected);
        claimRepository.save(claim4);

        ExpenseClaim claim5 = new ExpenseClaim();
        claim5.setEmployee(emp3);
        claim5.setDescription("Software subscription");
        claim5.setAmount(new BigDecimal("299.99"));
        claim5.setDateSubmitted(LocalDate.of(2025, 1, 18));
        claim5.setStatus(ExpenseClaim.ClaimStatus.Approved);
        claimRepository.save(claim5);

        System.out.println("Sample data loaded successfully!");
    }
}
