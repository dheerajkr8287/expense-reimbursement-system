package com.company.expense_reimbursement_system.service;


import com.company.expense_reimbursement_system.dto.ClaimResponse;
import com.company.expense_reimbursement_system.dto.CreateClaimRequest;
import com.company.expense_reimbursement_system.dto.ReportClaimData;
import com.company.expense_reimbursement_system.entity.Employee;
import com.company.expense_reimbursement_system.entity.ExpenseClaim;
import com.company.expense_reimbursement_system.exception.ResourceNotFoundException;
import com.company.expense_reimbursement_system.repository.EmployeeRepository;
import com.company.expense_reimbursement_system.repository.ExpenseClaimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseClaimService {

    @Autowired
    private final ExpenseClaimRepository claimRepository;
    @Autowired
    private final EmployeeRepository employeeRepository;

    @Transactional
    public ClaimResponse createClaim(Long employeeId, CreateClaimRequest request){

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id:" + employeeId));

        ExpenseClaim claim=new ExpenseClaim();
        claim.setEmployee(employee);
        claim.setDescription(request.getDescription());
        claim.setAmount(request.getAmount());
        claim.setDateSubmitted(LocalDate.now());
        claim.setStatus(ExpenseClaim.ClaimStatus.Pending);

        ExpenseClaim savedClaim=claimRepository.save(claim);
        return mapToClaimResponse(savedClaim);

    }



    @Transactional(readOnly = true)
    public List<ClaimResponse> getClaimsByEmployee(Long employeeId){
        if (!employeeRepository.existsById(employeeId)){
            throw new ResourceNotFoundException("Employee not found with id:"+employeeId);

        }

        List<ExpenseClaim> claims=claimRepository.findByEmployeeId(employeeId);
        return claims.stream()
                .map(this::mapToClaimResponse)
                .collect(Collectors.toList());


    }

    @Transactional
    public ClaimResponse updateClaimStatus(Long claimId,String status){

        ExpenseClaim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with id: " + claimId));

        ExpenseClaim.ClaimStatus claimStatus = ExpenseClaim.ClaimStatus.valueOf(status);
        claim.setStatus(claimStatus);

        ExpenseClaim updatedClaim = claimRepository.save(claim);
        return mapToClaimResponse(updatedClaim);
    }

    @Transactional(readOnly = true)
    public List<ReportClaimData> getClaimsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<ExpenseClaim> claims = claimRepository.findClaimsByDateRange(startDate, endDate);

        return claims.stream()
                .map(claim -> new ReportClaimData(
                        claim.getEmployee().getName(),
                        claim.getEmployee().getDepartment(),
                        claim.getDescription(),
                        claim.getAmount(),
                        claim.getStatus().name(),
                        claim.getDateSubmitted()
                ))
                .collect(Collectors.toList());
    }



    private ClaimResponse mapToClaimResponse(ExpenseClaim claim){
        return new ClaimResponse(
                claim.getId(),
                claim.getEmployee().getId(),
                claim.getEmployee().getName(),
                claim.getEmployee().getDepartment(),
                claim.getDateSubmitted(),
                claim.getDescription(),
                claim.getAmount(),
                claim.getStatus().name()
        );
    }







}
