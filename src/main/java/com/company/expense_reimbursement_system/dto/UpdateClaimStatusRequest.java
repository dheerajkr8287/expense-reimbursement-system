package com.company.expense_reimbursement_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClaimStatusRequest {

    @NotNull(message = "Status is required")
    @Pattern(regexp = "Approved|Rejected",message = "Status must be either Approved or Rejected")
    private String status;


}


/*
@Pattern is a validation annotation in Java (from javax.validation.constraints.Pattern).
It is used to check if a String value matches a specific regular expression (regex).

syntax:
@Pattern(regexp = "your-regex-here", message = "your-error-message-here")
private String fieldName;

Parameter	Meaning
regexp	    The regex (rule) that the value must match
message  	Error message shown when validation fails

regexp = "Approved|Rejected" → The value must be either "Approved" or "Rejected".
If it is anything else, validation fails.
The message "Status must be either Approved or Rejected" will be shown

When is @Pattern used?
It is mostly used for:
Validating request bodies in Spring Boot
Ensuring a user enters proper formatted text
Enforcing strict predefined values (status, code, names, etc.)

 */