package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.demo.enums.LoanStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name= "loan_application")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanApplication {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long loanId;
	
	@Column(unique=true)
	@NotBlank(message=" Application number is required")
	private String applicationNumber;
	
	
	@NotBlank(message=" Application name is required")
	private String applicationName;
	
	
	@Email
	private String email;
	
	
	@NotBlank
	private String mobileNumber;
	
	
	@NotNull
	@DecimalMin(value="1000")
	private BigDecimal loanAmount;
	
	
	@NotNull
	private Integer creditscore;
	
	
	@Enumerated(EnumType.STRING)
	private LoanStatus  loanStatus;
	
	private LocalDateTime submittedDate;
	
	@Version
	private Long version;
	
	
	@OneToOne(mappedBy = "loanApplication",
			              cascade= CascadeType.ALL)
	                  
	private Verification verification;

	
	@OneToOne(mappedBy = "loanApplication",
			   cascade=CascadeType.ALL)
	private Approval approval;
	
}
