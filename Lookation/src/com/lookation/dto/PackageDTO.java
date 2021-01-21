package com.lookation.dto;

public class PackageDTO
{
	private String code, apply_code, name
	             , time_start, time_end, price, apply_date;

	

	public String getApply_code()
	{
		return apply_code;
	}

	public void setApply_code(String apply_code)
	{
		this.apply_code = apply_code;
	}

	public String getApply_date()
	{
		return apply_date;
	}

	public void setApply_date(String apply_date)
	{
		this.apply_date = apply_date;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTime_start()
	{
		return time_start;
	}

	public void setTime_start(String time_start)
	{
		this.time_start = time_start;
	}

	public String getTime_end()
	{
		return time_end;
	}

	public void setTime_end(String time_end)
	{
		this.time_end = time_end;
	}

	public String getPrice()
	{
		return price;
	}

	public void setPrice(String price)
	{
		this.price = price;
	}
	
}
