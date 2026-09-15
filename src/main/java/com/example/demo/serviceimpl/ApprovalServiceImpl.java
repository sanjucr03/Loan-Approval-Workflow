package com.example.demo.serviceimpl;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoanResponseDTO;
import com.example.demo.entity.Approval;
import com.example.demo.entity.AuditLog;
import com.example.demo.entity.LoanApplication;
import com.example.demo.enums.DecisionStatus;
import com.example.demo.enums.LoanStatus;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.InvalidWorkflowException;
import com.example.demo.repository.ApprovalRepository;
import com.example.demo.repository.AuditRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.service.ApprovalService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalServiceImpl implements ApprovalService {

	
	 private final LoanRepository loanRepository;
	    private final ApprovalRepository approvalRepository;
	    private final AuditRepository auditRepository;
	    private final ModelMapper modelMapper;

	   
	    public LoanResponseDTO approve(Long loanId) {

	        LoanApplication loan = loanRepository.findById(loanId)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Loan not found"));

	        if (loan.getLoanStatus() != LoanStatus.RISK_ASSESSMENT) {
	            throw new InvalidWorkflowException(
	                    "Loan must complete Risk Assessment before approval");
	        }

	        Approval approval = approvalRepository
	                .findByLoanApplication_LoanId(loanId)
	                .orElse(new Approval());

	        approval.setLoanApplication(loan);
	        approval.setDecisionStatus(DecisionStatus.APPROVED);
	        approval.setRemarks("Loan Approved");
	        approval.setDecisionDate(LocalDateTime.now());

	        approvalRepository.save(approval);

	        loan.setLoanStatus(LoanStatus.APPROVED);

	        loanRepository.save(loan);

	        saveAudit(
	                loanId,
	                "Loan Approved",
	                "Approval Manager");

	        return modelMapper.map(loan, LoanResponseDTO.class);
	    }

	    
	    public LoanResponseDTO reject(Long loanId, String remarks) {

	        LoanApplication loan = loanRepository.findById(loanId)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Loan not found"));

	        if (loan.getLoanStatus() != LoanStatus.RISK_ASSESSMENT) {
	            throw new InvalidWorkflowException(
	                    "Loan must complete Risk Assessment before rejection");
	        }

	        Approval approval = approvalRepository
	                .findByLoanApplication_LoanId(loanId)
	                .orElse(new Approval());

	        approval.setLoanApplication(loan);
	        approval.setDecisionStatus(DecisionStatus.REJECTED);
	        approval.setRemarks(remarks);
	        approval.setDecisionDate(LocalDateTime.now());

	        approvalRepository.save(approval);

	        loan.setLoanStatus(LoanStatus.REJECTED);

	        loanRepository.save(loan);

	        saveAudit(
	                loanId,
	                "Loan Rejected",
	                "Approval Manager");

	        return modelMapper.map(loan, LoanResponseDTO.class);
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
