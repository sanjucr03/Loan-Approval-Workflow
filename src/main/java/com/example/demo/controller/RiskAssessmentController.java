package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoanResponseDTO;
import com.example.demo.service.RiskAssessmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/risk-assessment")
@RequiredArgsConstructor
public class RiskAssessmentController {
	
	 private final RiskAssessmentService riskAssessmentService;

	    @PutMapping("/{loanId}")
	    public ResponseEntity<ApiResponse<LoanResponseDTO>> assessRisk(
	            @PathVariable Long loanId) {

	        LoanResponseDTO response =
	                riskAssessmentService.assessRisk(loanId);

	        ApiResponse<LoanResponseDTO> apiResponse =
	                ApiResponse.<LoanResponseDTO>builder()
	                        .success(true)
	                        .message("Risk Assessment Completed Successfully")
	                        .data(response)
	                        .build();

	        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	    }

}
