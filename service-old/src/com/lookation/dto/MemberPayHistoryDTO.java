package com.lookation.dto;

public class MemberPayHistoryDTO
{
	private String mcode, lcode, name, payDate;
	private int price;
	
	// getter / setter 구성
	public String getMcode()
	{
		return mcode;
	}
	public void setMcode(String mcode)
	{
		this.mcode = mcode;
	}
	public String getLcode()
	{
		return lcode;
	}
	public void setLcode(String lcode)
	{
		this.lcode = lcode;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getPayDate()
	{
		return payDate;
	}
	public void setPayDate(String payDate)
	{
		this.payDate = payDate;
	}
	public int getPrice()
	{
		return price;
	}
	public void setPrice(int price)
	{
		this.price = price;
	}
	


}