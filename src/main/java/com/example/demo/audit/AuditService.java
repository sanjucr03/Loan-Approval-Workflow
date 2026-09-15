package com.example.demo.audit;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demo.entity.AuditLog;
import com.example.demo.repository.AuditRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditService {


	
	private final AuditRepository auditRepository;

    public void saveAudit(Long loanId,
                          String action,
                          String user){

        AuditLog log = AuditLog.builder()
                .loanId(loanId)
                .action(action)
                .performedBy(user)
                .actionTime(LocalDateTime.now())
                .build();

        auditRepository.save(log);

    }

}
