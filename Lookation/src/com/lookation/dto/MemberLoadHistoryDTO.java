package com.lookation.dto;

public class MemberLoadHistoryDTO
{
	private String bankNumber, bank, regDate, loadType, procDate;
	private int amount;

	// getter / setter 구성
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
