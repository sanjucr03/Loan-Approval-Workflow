package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.dto.LoanResponseDTO;

public interface LoanService {
	
	 LoanResponseDTO submitLoanApplication(LoanRequestDTO request);

	    LoanResponseDTO getLoanById(Long loanId);

	    List<LoanResponseDTO> getAllLoans();

	    LoanResponseDTO verifyLoan(Long loanId);

	    LoanResponseDTO performRiskAssessment(Long loanId);

	    LoanResponseDTO approveLoan(Long loanId);

	    LoanResponseDTO rejectLoan(Long loanId, String remarks);

	    void deleteLoan(Long loanId);

}
