package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.LoanResponseDTO;
import com.example.demo.service.VerificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/verifications")
@RequiredArgsConstructor
public class VerificationController {	

    private final VerificationService verificationService;

    @PutMapping("/{loanId}")
    public ResponseEntity<ApiResponse<LoanResponseDTO>> verifyLoan(
            @PathVariable Long loanId) {

        LoanResponseDTO response = verificationService.verifyDocuments(loanId);

        ApiResponse<LoanResponseDTO> apiResponse =
                ApiResponse.<LoanResponseDTO>builder()
                        .success(true)
                        .message("Loan verification completed successfully.")
                        .data(response)
                        .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
