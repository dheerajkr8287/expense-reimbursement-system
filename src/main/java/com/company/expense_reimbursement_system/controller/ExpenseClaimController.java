package com.company.expense_reimbursement_system.controller;

import com.company.expense_reimbursement_system.dto.*;
import com.company.expense_reimbursement_system.service.ExpenseClaimService;
import com.company.expense_reimbursement_system.service.PdfGenerationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExpenseClaimController {

    @Autowired
    private final ExpenseClaimService claimService;
    @Autowired
    private  final PdfGenerationService pdfService;

    @PostMapping("/employees/{id}/claims")
    public ResponseEntity<ApiResponse<ClaimResponse>> createClaim(
            @PathVariable Long id,
            @Valid @RequestBody CreateClaimRequest request){
        ClaimResponse claim = claimService.createClaim(id, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Claims created sucessfully",claim));
    }

    @GetMapping("/employees/{id}/claims")
    public ResponseEntity<ApiResponse<List<ClaimResponse>>> getClaimsByEmployee(@PathVariable Long id){
        List<ClaimResponse> claims = claimService.getClaimsByEmployee(id);

        if (claims.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.success("No claims found for thus employee",claims));
        }

        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(claims));
    }


    @PutMapping("/claims/{claimId}/status")
    public ResponseEntity<ApiResponse<ClaimResponse>> updateClaimStatus(
            @PathVariable Long claimId,
            @Valid @RequestBody UpdateClaimStatusRequest request){


        ClaimResponse claim =claimService.updateClaimStatus(claimId,request.getStatus());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Claim status updated successfully", claim));

    }



    @GetMapping("/reports/claims")
    public ResponseEntity<?> getClaimsReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end_date,
            @RequestParam(required = false, defaultValue = "json") String format) {

                                    // Validate date range
        if (start_date.isAfter(end_date)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("start_date cannot be after end_date"));
        }

        List<ReportClaimData> claims = claimService.getClaimsByDateRange(start_date, end_date);

                                                     // Return PDF format

        if ("pdf".equalsIgnoreCase(format)) {
            byte[] pdfBytes = pdfService.generateExpenseReport(claims, start_date, end_date);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "expense_report.pdf");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(pdfBytes);
        }

        // Return JSON format (default)
        if (claims.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponse.success("No claims found for the specified date range", claims));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(claims));
    }




}
