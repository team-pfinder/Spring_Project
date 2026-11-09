package com.lookation.dto;

public class HostExchangeHistoryDTO
{
	private String bankNumber, bank, exchangeDate;
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
	public String getExchangeDate()
	{
		return exchangeDate;
	}
	public void setExchangeDate(String exchangeDate)
	{
		this.exchangeDate = exchangeDate;
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
