package com.lendico.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lendico.model.BorrowerPaymentInfoResp;
import com.lendico.model.PaymentReq;
import com.lendico.service.PaymentService;



@RestController
public class PaymentController {
	
	
	@Autowired
	PaymentService paymentService;
	
	 @PostMapping(path ="/repaymentPlan/", produces = MediaType.APPLICATION_JSON_VALUE)
	 public BorrowerPaymentInfoResp getRepaymentPlan(@RequestBody PaymentReq paymentReq) {
		 
		 System.out.println("repaymentPlan");
		 BorrowerPaymentInfoResp  resp=paymentService.getRepaymentPlan(paymentReq);
		 
		 return resp;
	 }

	
}
