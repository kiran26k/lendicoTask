package com.lendico.serviceimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lendico.model.BorrowerPaymentInfoResp;
import com.lendico.model.PaymentReq;
import com.lendico.service.PaymentService;
import com.lendico.utility.CommonUtility;

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

	int daysInMonth = 30;
	public static final int YEAR_DAYS = 360;
	private double loanAmt;

	@Override
	public List<BorrowerPaymentInfoResp> getRepaymentPlan(PaymentReq paymentReq) {
		List<BorrowerPaymentInfoResp> repaymentPlan = new ArrayList<BorrowerPaymentInfoResp>();
		int duration = paymentReq.getDuration();
		loanAmt = paymentReq.getLoanAmount();
		int initalDuration = paymentReq.getDuration();
		double princple = getPrinciple(paymentReq);
		double interest = getInterest(paymentReq);
		double outstanding = paymentReq.getLoanAmount() - princple;
		double amt = getBorrowerAmount(interest, princple);
		int installments = 0;

		while (outstanding > 0 || duration != 0) {
			BorrowerPaymentInfoResp borrowerPaymentInfo = new BorrowerPaymentInfoResp();

			borrowerPaymentInfo.setBorrowerPaymentAmount(amt);
			if (duration == initalDuration) {
				borrowerPaymentInfo.setDate(CommonUtility.get_MM_DD_YY_StringDate(paymentReq.getStartDate()));
			} else {
				Date next = CommonUtility.getNextMonth(paymentReq.getStartDate(), installments++);
				borrowerPaymentInfo.setDate(CommonUtility.get_MM_DD_YY_StringDate(next));
			}
			borrowerPaymentInfo.setInitialOutstandingPrincipal(paymentReq.getLoanAmount());
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
		double intrest = (paymentReq.getNominalRate() / 100 * 30 * paymentReq.getLoanAmount()) / 360;
		return CommonUtility.getUptoTwoDecimal(intrest);
	}

	double getPrinciple(PaymentReq paymentReq) {
		double emi = getEmi(paymentReq);
		double intrest = getInterest(paymentReq);
		if (intrest > paymentReq.getLoanAmount()) {
			return paymentReq.getLoanAmount();
		}
		double principle = emi - intrest;
		return CommonUtility.getUptoTwoDecimal(principle);
	}

	double getBorrowerAmount(double interest, double principl) {
		return interest + principl;
	}

	double getEmi(PaymentReq paymentReq) {
		double principal = loanAmt;
		double rate = paymentReq.getNominalRate();
		double year = paymentReq.getDuration();
		rate = rate / (12 * 100);
		double annuity = (principal * rate * Math.pow(1 + rate, year)) / (Math.pow(1 + rate, year) - 1);
		return CommonUtility.getUptoTwoDecimal(annuity);
	}

}
