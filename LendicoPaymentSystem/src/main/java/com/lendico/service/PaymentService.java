package com.lendico.service;

import java.util.List;

import com.lendico.model.BorrowerPaymentInfoResp;
import com.lendico.model.PaymentReq;


public interface PaymentService {

	List<BorrowerPaymentInfoResp> getRepaymentPlan(PaymentReq paymentReq);
}
