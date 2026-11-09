package com.lookation.dto;

import java.util.Comparator;

public class ReviewCountCompare implements Comparator<SearchDTO>
{
	@Override
	public int compare(SearchDTO rc1, SearchDTO rc2)
	{
		return rc1.getReview_count() > rc2.getReview_count() ? 1
				: rc1.getReview_count() < rc2.getReview_count() ? -1 : 0;
	}
}
