package com.lendico.serviceimpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.http.Http;

import com.lendico.model.BorrowerPaymentInfoResp;
import com.lendico.model.PaymentReq;

@Configuration
public class PaymentServiceImpl {

	@Bean
	public IntegrationFlow getRepaymentPlan(PaymentReq paymentReq) {

		BorrowerPaymentInfoResp response = new BorrowerPaymentInfoResp();
		return flow -> flow.log()
				.handle(Http.outboundGateway("http://localhost:8080/amexsite/v1/corporate_accounts/mobility")
						.httpMethod(HttpMethod.GET).expectedResponseType(BorrowerPaymentInfoResp.class))
				.log().transform(BorrowerPaymentInfoResp.class, c -> c);
	}

}
