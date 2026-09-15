package com.example.demo.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.dto.LoanResponseDTO;
import com.example.demo.entity.Approval;
import com.example.demo.entity.AuditLog;
import com.example.demo.entity.LoanApplication;
import com.example.demo.entity.Verification;
import com.example.demo.enums.DecisionStatus;
import com.example.demo.enums.LoanStatus;
import com.example.demo.enums.RiskLevel;
import com.example.demo.exception.DuplicateApplicationException;
import com.example.demo.exception.InvalidWorkflowException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ApprovalRepository;
import com.example.demo.repository.AuditRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.repository.VerificationRepository;
import com.example.demo.service.LoanService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional
public class LoanServiceImpl implements LoanService {
	
	
	private final LoanRepository loanRepository;
    private final VerificationRepository verificationRepository;
    private final ApprovalRepository approvalRepository;
    private final AuditRepository auditRepository;
    private final ModelMapper modelMapper;

	@Override
	public LoanResponseDTO submitLoanApplication(LoanRequestDTO request) {
		// TODO Auto-generated method stub
		
		 if (loanRepository.existsByApplicationNumber(request.getApplicationNumber())) {
	            throw new DuplicateApplicationException("Loan Application Already Exists");
	        }

	        LoanApplication loan = modelMapper.map(request, LoanApplication.class);

	        loan.setLoanStatus(LoanStatus.SUBMITTED);
	        loan.setSubmittedDate(LocalDateTime.now());

	        LoanApplication savedLoan = loanRepository.save(loan);

	        saveAudit(savedLoan.getLoanId(),
	                "Loan Submitted",
	                "System");

	        return modelMapper.map(savedLoan, LoanResponseDTO.class);
	}

	@Override
	public LoanResponseDTO getLoanById(Long loanId) {
		// TODO Auto-generated method stub
		
		 LoanApplication loan = loanRepository.findById(loanId)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Loan Not Found"));

	        return modelMapper.map(loan, LoanResponseDTO.class);
	}

	@Override
	public List<LoanResponseDTO> getAllLoans() {
		// TODO Auto-generated method stub
		
		 return loanRepository.findAll()
	                .stream()
	                .map(loan -> modelMapper.map(loan, LoanResponseDTO.class))
	                .collect(Collectors.toList());
	}

	@Override
	public LoanResponseDTO verifyLoan(Long loanId) {
		// TODO Auto-generated method stub
		 
		
		 LoanApplication loan = loanRepository.findById(loanId)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Loan Not Found"));

	        if (loan.getLoanStatus() != LoanStatus.SUBMITTED) {
	            throw new InvalidWorkflowException("Loan must be in SUBMITTED state");
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

	        saveAudit(loan.getLoanId(),
	                "Loan Verified",
	                "Verification Officer");

	        return modelMapper.map(loan, LoanResponseDTO.class);
	}

	@Override
	public LoanResponseDTO performRiskAssessment(Long loanId) {
		// TODO Auto-generated method stub
		
		
		 LoanApplication loan = loanRepository.findById(loanId)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Loan Not Found"));

	        if (loan.getLoanStatus() != LoanStatus.VERIFIED) {
	            throw new InvalidWorkflowException("Loan must be VERIFIED");
	        }

	        Approval approval = new Approval();

	        if (loan.getCreditscore() >= 750) {

	            approval.setRiskLevel(RiskLevel.LOW);

	        } else if (loan.getCreditscore() >= 650) {

	            approval.setRiskLevel(RiskLevel.MEDIUM);

	        } else {

	            approval.setRiskLevel(RiskLevel.HIGH);
	        }

	        if (loan.getLoanAmount().doubleValue() <= 500000) {

	            approval.setDecisionStatus(DecisionStatus.APPROVED);

	        } else if (loan.getLoanAmount().doubleValue() <= 1500000) {

	            approval.setDecisionStatus(DecisionStatus.MANUAL_REVIEW);

	        } else {

	            approval.setDecisionStatus(DecisionStatus.REJECTED);
	        }

	        approval.setDecisionDate(LocalDateTime.now());
	        approval.setRemarks("Risk Assessment Completed");
	        approval.setLoanApplication(loan);

	        approvalRepository.save(approval);

	        loan.setLoanStatus(LoanStatus.RISK_ASSESSMENT);

	        loanRepository.save(loan);

	        saveAudit(loan.getLoanId(),
	                "Risk Assessment Completed",
	                "Risk Engine");

	        return modelMapper.map(loan, LoanResponseDTO.class);
	    }

	

	@Override
	public LoanResponseDTO approveLoan(Long loanId) {
		// TODO Auto-generated method stub
		
		 LoanApplication loan = loanRepository.findById(loanId)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Loan Not Found"));

	        if (loan.getLoanStatus() != LoanStatus.RISK_ASSESSMENT) {
	            throw new InvalidWorkflowException("Risk Assessment Pending");
	        }

	        loan.setLoanStatus(LoanStatus.APPROVED);

	        loanRepository.save(loan);

	        saveAudit(loan.getLoanId(),
	                "Loan Approved",
	                "Manager");

	        return modelMapper.map(loan, LoanResponseDTO.class);
	}

	@Override
	public LoanResponseDTO rejectLoan(Long loanId, String remarks) {
		// TODO Auto-generated method stub
		
		
		 LoanApplication loan = loanRepository.findById(loanId)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Loan Not Found"));

	        if (loan.getLoanStatus() != LoanStatus.RISK_ASSESSMENT) {
	            throw new InvalidWorkflowException("Risk Assessment Pending");
	        }

	        loan.setLoanStatus(LoanStatus.REJECTED);

	        loanRepository.save(loan);

	        Approval approval = approvalRepository
	                .findById(loanId)
	                .orElse(new Approval());

	        approval.setRemarks(remarks);
	        approval.setDecisionStatus(DecisionStatus.REJECTED);
	        approval.setDecisionDate(LocalDateTime.now());
	        approval.setLoanApplication(loan);

	        approvalRepository.save(approval);

	        saveAudit(loan.getLoanId(),
	                "Loan Rejected",
	                "Manager");

	        return modelMapper.map(loan, LoanResponseDTO.class);
	}

	@Override
	public void deleteLoan(Long loanId) {
		// TODO Auto-generated method stub
		
		 LoanApplication loan = loanRepository.findById(loanId)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Loan Not Found"));

	        loanRepository.delete(loan);

	        saveAudit(loanId,
	                "Loan Deleted",
	                "Admin");
	    }

	    private void saveAudit(Long loanId,
	                           String action,
	                           String performedBy) {

	        AuditLog audit = AuditLog.builder()
	                .loanId(loanId)
	                .action(action)
	                .performedBy(performedBy)
	                .actionTime(LocalDateTime.now())
	                .build();

	        auditRepository.save(audit);
	}

}
