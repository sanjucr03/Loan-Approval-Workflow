package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.LoanApplication;
import com.example.demo.enums.LoanStatus;

public interface LoanRepository extends JpaRepository<LoanApplication, Long> {
	
	 Optional<LoanApplication> findByApplicationNumber(String applicationNumber);

	    boolean existsByApplicationNumber(String applicationNumber);

	    long countByLoanStatus(LoanStatus loanStatus);

}
