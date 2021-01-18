package com.lookation.dto;

public class UserLoadManagerDTO
{
	private String email, bankNumber, bank, bankHolder, regDate, loadType, procDate;
	private int amount;
	
	// getter / setter 구성
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getBankNumber()
	{
		return bankNumber;
	}
	public void setBankNumber(String bankNumber)
	{
		this.bankNumber = bankNumber;
	}
	public String getBank()
	{
		return bank;
	}
	public void setBank(String bank)
	{
		this.bank = bank;
	}
	public String getBankHolder()
	{
		return bankHolder;
	}
	public void setBankHolder(String bankHolder)
	{
		this.bankHolder = bankHolder;
	}
	public String getRegDate()
	{
		return regDate;
	}
	public void setRegDate(String regDate)
	{
		this.regDate = regDate;
	}
	public String getLoadType()
	{
		return loadType;
	}
	public void setLoadType(String loadType)
	{
		this.loadType = loadType;
	}
	public String getProcDate()
	{
		return procDate;
	}
	public void setProcDate(String procDate)
	{
		this.procDate = procDate;
	}
	public int getAmount()
	{
		return amount;
	}
	public void setAmount(int amount)
	{
		this.amount = amount;
	}
}
