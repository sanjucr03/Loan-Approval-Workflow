package com.example.demo.service;

import com.example.demo.dto.LoanResponseDTO;

public interface ApprovalService {

	
	LoanResponseDTO approve(Long loanId);

    LoanResponseDTO reject(Long loanId, String remarks);
}
