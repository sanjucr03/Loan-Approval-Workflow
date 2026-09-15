package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AuditLog;

public interface AuditRepository extends JpaRepository<AuditLog, Long>{
	
	List<AuditLog> findByLoanId(Long loanId);

}
