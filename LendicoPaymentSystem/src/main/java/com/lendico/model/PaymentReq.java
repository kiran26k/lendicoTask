package com.lendico.model;

import java.util.Date;

public class PaymentReq {

	private long loanAmount;
	private double nominalRate;
	private int durationl;
	private Date startDate;

	public long getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(long loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getNominalRate() {
		return nominalRate;
	}

	public void setNominalRate(double nominalRate) {
		this.nominalRate = nominalRate;
	}

	public int getDurationl() {
		return durationl;
	}

	public void setDurationl(int durationl) {
		this.durationl = durationl;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "PaymentReq [loanAmount=" + loanAmount + ", nominalRate=" + nominalRate + ", durationl=" + durationl
				+ ", startDate=" + startDate + "]";
	}

}
