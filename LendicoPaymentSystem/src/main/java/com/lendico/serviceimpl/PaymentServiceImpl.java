package com.lendico.serviceimpl;

import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.http.Http;
import org.springframework.stereotype.Service;

import com.lendico.model.BorrowerPaymentInfoResp;
import com.lendico.model.PaymentReq;


@Service("paymentService")
public class PaymentServiceImpl {

	int daysInMonth = 30;

	@Bean
	public IntegrationFlow getRepaymentPlan(PaymentReq paymentReq) {
		System.out.println(" getRepaymentPlan ");
		BorrowerPaymentInfoResp response = new BorrowerPaymentInfoResp();
		double amt = getBorrowerAmount(getInterest(paymentReq), getPrinciple());
		response.setBorrowerPaymentAmount(amt);
		response.setDate(new Date());
		response.setDurationl(100);
		
		
		return flow -> flow.log()
				.handle(Http.outboundGateway("http://localhost:8080/amexsite/v1/corporate_accounts/mobility")
						.httpMethod(HttpMethod.POST).expectedResponseType(BorrowerPaymentInfoResp.class))
				.log().transform(BorrowerPaymentInfoResp.class, c -> c);
	}

	double getInterest(PaymentReq paymentReq) {

		// (Nominal-Rate * Days in Month * Initial Outstanding Principal) / days in year
		// e.g. first installment Interest = (5.00 * 30 * 5000 / 360) = 2083.33333333
		// cents
		double principle = getPrinciple();
		return (paymentReq.getNominalRate() * daysInMonth * principle) / 360;
	}

	double getPrinciple() {
		// Annuity	(Borrower Payment Amount) Need to calculate
		// Principal = Annuity - Interest (if, calculated interest amount exceeds the
		// initial outstanding principal amount, take initial outstanding principal amount instead)

		return 0.0;
	}

	double getBorrowerAmount(double interest, double principl) {
		return interest + principl;
	}

}
