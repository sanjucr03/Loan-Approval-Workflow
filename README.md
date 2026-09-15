# Loan Approval Workflow System

A Spring Boot REST API application that automates the loan approval
process by managing loan applications, document verification, risk
assessment, and final approval/rejection.

## Features

-   Create Loan Application
-   View All Loan Applications
-   View Loan by ID
-   Verify Loan Documents
-   Assess Loan Risk
-   Approve Loan
-   Reject Loan
-   Delete Loan Application
-   Audit Log for Workflow Tracking
-   Global Exception Handling
-   Input Validation using Bean Validation
-   Swagger (OpenAPI) Documentation

## Workflow

    Loan Application
            ↓
    Document Verification
            ↓
    Risk Assessment
            ↓
    Approve / Reject

## Technologies Used

-   Java 17
-   Spring Boot
-   Spring Data JPA
-   Hibernate
-   MySQL
-   Maven
-   Lombok
-   ModelMapper
-   Swagger (OpenAPI)
-   Postman


## REST APIs

-   POST /api/loans
-   GET /api/loans
-   GET /api/loans/{id}
-   DELETE /api/loans/{id}
-   PUT /api/verifications/{loanId}
-   PUT /api/risk-assessment/{loanId}
-   PUT /api/loans/{loanId}/approve
-   PUT /api/loans/{loanId}/reject

## Loan Status

-   SUBMITTED
-   VERIFIED
-   RISK_ASSESSMENT
-   APPROVED
-   REJECTED

## Risk Levels

-   LOW
-   MEDIUM
-   HIGH



## Author

**Sanjay CR**



