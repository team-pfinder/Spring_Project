package com.lookation.dto;

public class SearchDTO implements Comparable<SearchDTO>
{
	private String loc_code, loc_type, loc_name, loc_addr, loc_addr_city
	, loc_addr_dong, thumbnail_url
	, max_people, apply_date
	, keyword, start_date, end_date;
	
	private int price, review_count, start_price, end_price;  
	private double avg_rate;


	public String getLoc_code()
	{
		return loc_code;
	}
	
	public void setLoc_code(String loc_code)
	{
		this.loc_code = loc_code;
	}
	
	public String getLoc_type()
	{
		return loc_type;
	}

	public void setLoc_type(String loc_type)
	{
		this.loc_type = loc_type;
	}

	public String getLoc_name()
	{
		return loc_name;
	}

	public void setLoc_name(String loc_name)
	{
		this.loc_name = loc_name;
	}

	public String getLoc_addr()
	{
		return loc_addr;
	}

	public void setLoc_addr(String loc_addr)
	{
		this.loc_addr = loc_addr;
	}

	public String getLoc_addr_city()
	{
		return loc_addr_city;
	}

	public void setLoc_addr_city(String loc_addr_city)
	{
		this.loc_addr_city = loc_addr_city;
	}

	public String getLoc_addr_dong()
	{
		return loc_addr_dong;
	}

	public void setLoc_addr_dong(String loc_addr_dong)
	{
		this.loc_addr_dong = loc_addr_dong;
	}

	public String getThumbnail_url()
	{
		return thumbnail_url;
	}

	public void setThumbnail_url(String thumbnail_url)
	{
		this.thumbnail_url = thumbnail_url;
	}

	public String getMax_people()
	{
		return max_people;
	}

	public void setMax_people(String max_people)
	{
		this.max_people = max_people;
	}

	public String getApply_date()
	{
		return apply_date;
	}

	public void setApply_date(String apply_date)
	{
		this.apply_date = apply_date;
	}

	public String getKeyword()
	{
		return keyword;
	}

	public void setKeyword(String keyword)
	{
		this.keyword = keyword;
	}

	public String getStart_date()
	{
		return start_date;
	}

	public void setStart_date(String start_date)
	{
		this.start_date = start_date;
	}

	public String getEnd_date()
	{
		return end_date;
	}

	public void setEnd_date(String end_date)
	{
		this.end_date = end_date;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	public int getReview_count()
	{
		return review_count;
	}

	public void setReview_count(int review_count)
	{
		this.review_count = review_count;
	}

	public int getStart_price()
	{
		return start_price;
	}

	public void setStart_price(int start_price)
	{
		this.start_price = start_price;
	}

	public int getEnd_price()
	{
		return end_price;
	}

	public void setEnd_price(int end_price)
	{
		this.end_price = end_price;
	}

	public double getAvg_rate()
	{
		return avg_rate;
	}

	public void setAvg_rate(double avg_rate)
	{
		this.avg_rate = avg_rate;
	}

	@Override
	public int compareTo(SearchDTO s)
	{
		if(this.price > s.price)
			return 1;
		else if(this.price < s.price)
			return -1;
		else
			return 0;
	}
}
