package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class AuditLog {
	
   	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long auditId;

	    private Long loanId;

	    private String action;

	    private String performedBy;

	    private LocalDateTime actionTime;

	}


