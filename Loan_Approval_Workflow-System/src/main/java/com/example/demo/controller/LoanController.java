package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoanRequestDTO;
import com.example.demo.dto.LoanResponseDTO;
import com.example.demo.service.LoanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/loans")
public class LoanController {
	
	private final LoanService loanService;

    @PostMapping
    public ResponseEntity<ApiResponse<LoanResponseDTO>> submitLoan(
            @Valid @RequestBody LoanRequestDTO request){

        LoanResponseDTO response =
                loanService.submitLoanApplication(request);

        return ResponseEntity.ok(
                ApiResponse.<LoanResponseDTO>builder()
                        .success(true)
                        .message("Loan Application Submitted Successfully")
                        .data(response)
                        .build());

    }

    @GetMapping("/{loanId}")
    public ResponseEntity<ApiResponse<LoanResponseDTO>> getLoan(
            @PathVariable Long loanId){

        return ResponseEntity.ok(
                ApiResponse.<LoanResponseDTO>builder()
                        .success(true)
                        .message("Loan Found")
                        .data(loanService.getLoanById(loanId))
                        .build());

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<LoanResponseDTO>>> getAllLoans(){

        return ResponseEntity.ok(
                ApiResponse.<List<LoanResponseDTO>>builder()
                        .success(true)
                        .message("Loan List")
                        .data(loanService.getAllLoans())
                        .build());

    }

    @PutMapping("/{loanId}/verify")
    public ResponseEntity<ApiResponse<LoanResponseDTO>> verifyLoan(
            @PathVariable Long loanId){

        return ResponseEntity.ok(
                ApiResponse.<LoanResponseDTO>builder()
                        .success(true)
                        .message("Loan Verified")
                        .data(loanService.verifyLoan(loanId))
                        .build());

    }

    @PutMapping("/{loanId}/risk")
    public ResponseEntity<ApiResponse<LoanResponseDTO>> assessRisk(
            @PathVariable Long loanId){

        return ResponseEntity.ok(
                ApiResponse.<LoanResponseDTO>builder()
                        .success(true)
                        .message("Risk Assessment Completed")
                        .data(loanService.performRiskAssessment(loanId))
                        .build());

    }

    @PutMapping("/{loanId}/approve")
    public ResponseEntity<ApiResponse<LoanResponseDTO>> approveLoan(
            @PathVariable Long loanId){

        return ResponseEntity.ok(
                ApiResponse.<LoanResponseDTO>builder()
                        .success(true)
                        .message("Loan Approved")
                        .data(loanService.approveLoan(loanId))
                        .build());

    }

    @PutMapping("/{loanId}/reject")
    public ResponseEntity<ApiResponse<LoanResponseDTO>> rejectLoan(
            @PathVariable Long loanId,
            @RequestParam String remarks){

        return ResponseEntity.ok(
                ApiResponse.<LoanResponseDTO>builder()
                        .success(true)
                        .message("Loan Rejected")
                        .data(loanService.rejectLoan(loanId, remarks))
                        .build());

    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<ApiResponse<String>> deleteLoan(
            @PathVariable Long loanId){

        loanService.deleteLoan(loanId);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Loan Deleted Successfully")
                        .data("Deleted")
                        .build());

    }

}
