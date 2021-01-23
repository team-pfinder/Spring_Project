package com.lookation.dto;

import java.util.Comparator;

public class AvgRateCompare implements Comparator<SearchDTO>
{
	@Override
	public int compare(SearchDTO ar1, SearchDTO ar2)
	{
		return ar1.getAvg_rate() > ar2.getAvg_rate() ? 1
				: ar1.getAvg_rate() < ar2.getAvg_rate() ? -1 : 0;
	}
}
