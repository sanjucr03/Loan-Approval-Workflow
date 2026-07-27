package com.example.demo.service;

import com.example.demo.dto.LoanResponseDTO;

public interface RiskAssessmentService {
	
	LoanResponseDTO assessRisk(Long loanId);

}
