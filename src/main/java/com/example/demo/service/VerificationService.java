package com.example.demo.service;

import com.example.demo.dto.LoanResponseDTO;

public interface VerificationService {
	
	LoanResponseDTO verifyDocuments(Long loanId);


}
