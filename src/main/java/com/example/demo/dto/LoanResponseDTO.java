package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.demo.enums.LoanStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanResponseDTO {

	private Long loanId;
    private String applicationNumber;
    private String applicationName;
    private BigDecimal loanAmount;
    private Integer creditScore;
    private LoanStatus loanStatus;
    private LocalDateTime submittedDate;
}
