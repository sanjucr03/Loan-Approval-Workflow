package com.example.demo.entity;

import java.time.LocalDateTime;

import com.example.demo.enums.DecisionStatus;
import com.example.demo.enums.RiskLevel;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Approval {

	
	    @Id  
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long approvalId;


	    @Enumerated(EnumType.STRING)
	    private RiskLevel riskLevel;


	    @Enumerated(EnumType.STRING)
	    private DecisionStatus decisionStatus;


	    private String remarks;


	    private LocalDateTime decisionDate;


	    @OneToOne
	    @JoinColumn(name = "loan_id")
	    private LoanApplication loanApplication;

	}

