package com.lookation.dto;

public class HostCalHistoryDTO
{
	private String lname, nickname, status, refundDate, applyDate, hostCancelDate;
	private int price, calAmount; // 3 5

	// getter / setter 구성
	public String getLname()
	{
		return lname;
	}
	public void setLname(String lname)
	{
		this.lname = lname;
	}
	public String getNickname()
	{
		return nickname;
	}
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}
	public String getStatus()
	{
		return status;
	}
	public void setStatus(String status)
	{
		this.status = status;
	}
	public String getRefundDate()
	{
		return refundDate;
	}
	public void setRefundDate(String refundDate)
	{
		this.refundDate = refundDate;
	}
	public String getApplyDate()
	{
		return applyDate;
	}
	public void setApplyDate(String applyDate)
	{
		this.applyDate = applyDate;
	}
	public String getHostCancelDate()
	{
		return hostCancelDate;
	}
	public void setHostCancelDate(String hostCancelDate)
	{
		this.hostCancelDate = hostCancelDate;
	}
	public int getPrice()
	{
		return price;
	}
	public void setPrice(int price)
	{
		this.price = price;
	}
	public int getCalAmount()
	{
		return calAmount;
	}
	public void setCalAmount(int calAmount)
	{
		this.calAmount = calAmount;
	}

}
