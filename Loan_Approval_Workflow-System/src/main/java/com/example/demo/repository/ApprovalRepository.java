package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Approval;

public interface ApprovalRepository extends JpaRepository<Approval, Long>{
	
	 Optional<Approval> findByLoanApplication_LoanId(Long loanId);

}
