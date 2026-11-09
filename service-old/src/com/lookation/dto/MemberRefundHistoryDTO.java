package com.lookation.dto;

public class MemberRefundHistoryDTO
{
	private String type, lname, applyDate, cancelDate;
	private int price, refundAmount;
	
	// getter / setter 구성
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public String getLname()
	{
		return lname;
	}
	public void setLname(String lname)
	{
		this.lname = lname;
	}
	public String getApplyDate()
	{
		return applyDate;
	}
	public void setApplyDate(String applyDate)
	{
		this.applyDate = applyDate;
	}
	public String getCancelDate()
	{
		return cancelDate;
	}
	public void setCancelDate(String cancelDate)
	{
		this.cancelDate = cancelDate;
	}
	public int getPrice()
	{
		return price;
	}
	public void setPrice(int price)
	{
		this.price = price;
	}
	public int getRefundAmount()
	{
		return refundAmount;
	}
	public void setRefundAmount(int refundAmount)
	{
		this.refundAmount = refundAmount;
	}
	
	
}
