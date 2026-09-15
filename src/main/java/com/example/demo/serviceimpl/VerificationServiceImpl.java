package com.example.demo.serviceimpl;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.LoanResponseDTO;
import com.example.demo.entity.LoanApplication;
import com.example.demo.entity.Verification;
import com.example.demo.enums.LoanStatus;
import com.example.demo.exception.InvalidWorkflowException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.VerificationRepository;
import com.example.demo.service.VerificationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VerificationServiceImpl implements VerificationService {
	
	 private final LoanRepository  loanRepository;
	    private final VerificationRepository verificationRepository;
	    private final ModelMapper modelMapper;

	   
	    public LoanResponseDTO verifyDocuments(Long loanId) {

	        LoanApplication loan = loanRepository.findById(loanId)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Loan not found"));

	        if (loan.getLoanStatus() != LoanStatus.SUBMITTED) {
	            throw new InvalidWorkflowException(
	                    "Loan must be in SUBMITTED status");
	        }

	        Verification verification = new Verification();

	        verification.setDocumentVerified(true);
	        verification.setIncomeVerified(true);
	        verification.setVerifierName("Verification Officer");
	        verification.setVerifiedDate(LocalDateTime.now());
	        verification.setLoanApplication(loan);

	        verificationRepository.save(verification);

	        loan.setLoanStatus(LoanStatus.VERIFIED);

	        loanRepository.save(loan);

	        return modelMapper.map(loan, LoanResponseDTO.class);
	    }

}
