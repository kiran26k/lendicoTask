package com.lendico.model;

import java.util.Date;

public class PaymentReq {

	private double loanAmount;
	private double nominalRate;
	private int duration;
	private Date startDate;

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getNominalRate() {
		return nominalRate;
	}

	public void setNominalRate(double nominalRate) {
		this.nominalRate = nominalRate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int durationl) {
		this.duration = durationl;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public String toString() {
		return "PaymentReq [loanAmount=" + loanAmount + ", nominalRate=" + nominalRate + ", duration=" + duration
				+ ", startDate=" + startDate + "]";
	}

}
