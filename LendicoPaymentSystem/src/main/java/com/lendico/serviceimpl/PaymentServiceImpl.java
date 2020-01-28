package com.lendico.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lendico.model.BorrowerPaymentInfoResp;
import com.lendico.model.PaymentReq;
import com.lendico.service.PaymentService;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

	int daysInMonth = 30;
	public static final int YEAR_DAYS = 360;

	@Override
	public List<BorrowerPaymentInfoResp> getRepaymentPlan(PaymentReq paymentReq) {
		List<BorrowerPaymentInfoResp> response = new ArrayList<BorrowerPaymentInfoResp>();

		BorrowerPaymentInfoResp borrowerPaymentInfo = new BorrowerPaymentInfoResp();

		int duration = paymentReq.getDurationl();
		double princple = getPrinciple(paymentReq);
		double interest = getInterest(paymentReq);
		double outstanding = paymentReq.getLoanAmount() - princple;
		double amt = getBorrowerAmount(interest, princple);

		while (outstanding > 0) {
			borrowerPaymentInfo.setBorrowerPaymentAmount(amt);
			borrowerPaymentInfo.setDate(new Date());
			borrowerPaymentInfo.setDurationl(duration);
			borrowerPaymentInfo.setInterest(interest);
			borrowerPaymentInfo.setPrincipal(princple);
			borrowerPaymentInfo.setRemainingOutstandingPrincipal(outstanding);
		
			duration = paymentReq.getDurationl();
			princple = getPrinciple(paymentReq);
			interest = getInterest(paymentReq);
			outstanding = paymentReq.getLoanAmount() - princple;
			System.out.println("outstanding "+outstanding);
			amt = getBorrowerAmount(interest, princple);
		
		}

		return response;
	}

	double getInterest(PaymentReq paymentReq) {

		// (Nominal-Rate * Days in Month * Initial Outstanding Principal) / days in year
		// e.g. first installment Interest = (5.00 * 30 * 5000 / 360) = 2083.33333333
		// cents
		return (paymentReq.getNominalRate() * daysInMonth * paymentReq.getLoanAmount()) / YEAR_DAYS;
	}

	double getPrinciple(PaymentReq paymentReq) {
		// Annuity (Borrower Payment Amount) Need to calculate
		// Principal = Annuity - Interest (if, calculated interest amount exceeds the
		// initial outstanding principal amount, take initial outstanding principal
		// amount instead)
		if(getInterest(paymentReq) > paymentReq.getLoanAmount()) {
			return paymentReq.getLoanAmount();
		}
		System.out.println("getPrinciple ");
		return getAnuity(paymentReq) - getInterest(paymentReq);
	}

	double getBorrowerAmount(double interest, double principl) {
		System.out.println("getBorrowerAmount "+interest + principl);
		return interest + principl;
	}

	double getAnuity(PaymentReq paymentReq) {
		double rate = paymentReq.getNominalRate() / 100;
		int durationInMonth = paymentReq.getDurationl() / 30;
		double dat = (1 - (1 / (1 + rate) * durationInMonth));
		double annuity = dat / paymentReq.getNominalRate();
		System.out.println("annuity "+annuity);
		return annuity;
	}

}
