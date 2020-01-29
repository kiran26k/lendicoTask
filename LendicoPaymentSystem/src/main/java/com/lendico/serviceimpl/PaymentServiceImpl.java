package com.lendico.serviceimpl;

import java.util.ArrayList;
import java.util.Calendar;
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
		List<BorrowerPaymentInfoResp> repaymentPlan = new ArrayList<BorrowerPaymentInfoResp>();
		int duration = paymentReq.getDuration() * 12;
		double princple = getPrinciple(paymentReq);
		double interest = getInterest(paymentReq);
		double outstanding = paymentReq.getLoanAmount() - princple;
		double amt = getBorrowerAmount(interest, princple);
	
		while (outstanding > 0 || duration != 0) {
			BorrowerPaymentInfoResp borrowerPaymentInfo = new BorrowerPaymentInfoResp();
			borrowerPaymentInfo.setInitialOutstandingPrincipal(paymentReq.getLoanAmount());
			borrowerPaymentInfo.setBorrowerPaymentAmount(amt);
			borrowerPaymentInfo.setDate(new Date());
			borrowerPaymentInfo.setDurationl(duration);
			borrowerPaymentInfo.setInterest(interest);
			borrowerPaymentInfo.setPrincipal(princple);
			borrowerPaymentInfo.setRemainingOutstandingPrincipal(outstanding);
			repaymentPlan.add(borrowerPaymentInfo);
			
			paymentReq.setLoanAmount(outstanding);
			princple = getPrinciple(paymentReq);
			interest = getInterest(paymentReq);
			outstanding = paymentReq.getLoanAmount() - princple;
			amt = getBorrowerAmount(interest, princple);
			duration--;
		}
		return repaymentPlan;
	}

	double getInterest(PaymentReq paymentReq) {
		// (Nominal-Rate * Days in Month * Initial Outstanding Principal) / days in year
		// e.g. first installment Interest = (5.00 * 30 * 5000 / 360) = 2083.33333333
		// cents
		double intrest = (paymentReq.getNominalRate() / 100 * 30 * paymentReq.getLoanAmount()) / 360;
		return (Math.round((intrest) * 100.0) / 100.0);
	}

	double getPrinciple(PaymentReq paymentReq) {
		// Annuity (Borrower Payment Amount) Need to calculate
		// Principal = Annuity - Interest (if, calculated interest amount exceeds the
		// initial outstanding principal amount, take initial outstanding principal
		// amount instead)
		double emi = getEmi(paymentReq);
		double intrest = getInterest(paymentReq);
		if (intrest > paymentReq.getLoanAmount()) {
			return paymentReq.getLoanAmount();
		}
		double principle = emi - intrest;
		return (Math.round((principle) * 100.0) / 100.0);
	}

	double getBorrowerAmount(double interest, double principl) {
		System.out.println("getBorrowerAmount " + interest + principl);
		return interest + principl;
	}

	private static double getEmi(PaymentReq paymentReq) {
		double principal = 5000;
		double rate = paymentReq.getNominalRate();
		double year = paymentReq.getDuration();
		rate = rate / (12 * 100);
		year = year * 12;
		double annuity = (principal * rate * Math.pow(1 + rate, year)) / (Math.pow(1 + rate, year) - 1);
		return (Math.round((annuity) * 100.0) / 100.0);
	}
	
	
	
	

}
