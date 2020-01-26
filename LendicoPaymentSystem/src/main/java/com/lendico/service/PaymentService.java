package com.lendico.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import com.lendico.model.BorrowerPaymentInfoResp;
import com.lendico.model.PaymentReq;


@MessagingGateway
public interface PaymentService {
	

	@Gateway(requestChannel = "getRepaymentPlan.input")
	BorrowerPaymentInfoResp getRepaymentPlan(PaymentReq paymentReq);
}
