package com.example.demo.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestDTO {
	
	    @NotBlank(message = "Application Number is required")
	    private String applicationNumber;

	    @NotBlank(message = "Applicant Name is required")
	    private String applicationName;

	    @Email(message = "Invalid Email")
	    private String email;

	    @NotBlank(message = "Mobile Number is required")
	    private String mobileNumber;

	    @NotNull(message = "Loan Amount is required")
	    @DecimalMin(value = "1000")
	    private BigDecimal loanAmount;

	    @NotNull(message = "Credit Score is required")
	    @Min(300)
	    @Max(900)
	    private Integer creditScore;

}
