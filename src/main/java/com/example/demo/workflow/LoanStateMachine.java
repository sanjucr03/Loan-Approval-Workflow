package com.example.demo.workflow;

import com.example.demo.enums.LoanStatus;
import com.example.demo.exception.InvalidWorkflowException;

public class LoanStateMachine {

	
	 public static LoanStatus nextState(LoanStatus currentStatus) {

	        return switch (currentStatus) {

	            case SUBMITTED -> LoanStatus.VERIFIED;

	            case VERIFIED -> LoanStatus.RISK_ASSESSMENT;

	            case RISK_ASSESSMENT ->
	                    throw new InvalidWorkflowException(
	                            "Use Approve or Reject after Risk Assessment");

	            default ->
	                    throw new InvalidWorkflowException(
	                            "Invalid Loan Workflow");

	        };

	    }
}
