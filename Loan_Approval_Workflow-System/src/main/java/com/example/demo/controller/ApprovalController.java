package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.ApprovalDTO;
import com.example.demo.dto.LoanResponseDTO;
import com.example.demo.service.ApprovalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/approvals")
public class ApprovalController {
	
	 private final ApprovalService approvalService;

	    @PutMapping("/{loanId}/approve")
	    public ResponseEntity<ApiResponse<LoanResponseDTO>> approveLoan(
	            @PathVariable Long loanId) {

	        LoanResponseDTO response = approvalService.approve(loanId);

	        ApiResponse<LoanResponseDTO> apiResponse =
	                ApiResponse.<LoanResponseDTO>builder()
	                        .success(true)
	                        .message("Loan approved successfully.")
	                        .data(response)
	                        .build();

	        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	    }

	    @PutMapping("/{loanId}/reject")
	    public ResponseEntity<ApiResponse<LoanResponseDTO>> rejectLoan(
	            @PathVariable Long loanId,
	            @Valid @RequestBody ApprovalDTO approvalDTO) {

	        LoanResponseDTO response =
	                approvalService.reject(loanId, approvalDTO.getRemarks());

	        ApiResponse<LoanResponseDTO> apiResponse =
	                ApiResponse.<LoanResponseDTO>builder()
	                        .success(true)
	                        .message("Loan rejected successfully.")
	                        .data(response)
	                        .build();

	        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	    }

}
