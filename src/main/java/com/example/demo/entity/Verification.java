package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
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
public class Verification {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long verificationId;

    private boolean documentVerified;

    private boolean incomeVerified;

    private String verifierName;

    private LocalDateTime verifiedDate;




    @OneToOne
    @JoinColumn(name = "loan_id")
    private LoanApplication loanApplication;

}

