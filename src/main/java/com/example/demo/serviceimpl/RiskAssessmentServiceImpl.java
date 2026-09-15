package com.example.demo.serviceimpl;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoanResponseDTO;
import com.example.demo.entity.Approval;
import com.example.demo.entity.AuditLog;
import com.example.demo.entity.LoanApplication;
import com.example.demo.enums.LoanStatus;
import com.example.demo.enums.RiskLevel;
import com.example.demo.exception.InvalidWorkflowException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ApprovalRepository;
import com.example.demo.repository.AuditRepository;
import com.example.demo.repository.LoanRepository;
import com.example.demo.service.RiskAssessmentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RiskAssessmentServiceImpl  implements RiskAssessmentService{
	
	private final LoanRepository loanRepository;
    private final ApprovalRepository approvalRepository;
    private final AuditRepository auditRepository;
    private final ModelMapper modelMapper;

    @Override
    public LoanResponseDTO assessRisk(Long loanId) {

        LoanApplication loan = loanRepository.findById(loanId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Loan not found"));

        if (loan.getLoanStatus() != LoanStatus.VERIFIED) {
            throw new InvalidWorkflowException(
                    "Loan must be VERIFIED before Risk Assessment");
        }

        Approval approval = approvalRepository
                .findByLoanApplication_LoanId(loanId)
                .orElse(new Approval());

        approval.setLoanApplication(loan);

        RiskLevel riskLevel;

        if (loan.getCreditscore() >= 750) {
            riskLevel = RiskLevel.LOW;
            approval.setDecisionStatus(null);
            approval.setRemarks("Risk Assessment Completed");
        }
        else if (loan.getCreditscore() >= 650) {
            riskLevel = RiskLevel.MEDIUM;
            approval.setDecisionStatus(null);
            approval.setRemarks("Risk Assessment Completed");
        }
        else {
            riskLevel = RiskLevel.HIGH;
            approval.setDecisionStatus(null);
            approval.setRemarks("Risk Assessment Completed");
        }

        approval.setRiskLevel(riskLevel);
        approval.setDecisionDate(LocalDateTime.now());

        approvalRepository.save(approval);

        loan.setLoanStatus(LoanStatus.RISK_ASSESSMENT);

        loanRepository.save(loan);

        saveAudit(
                loanId,
                "Risk Assessment Completed",
                "Risk Officer");

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
