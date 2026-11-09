package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.MainLocationDTO;

public interface IMainLocationInfo
{
	public ArrayList<MainLocationDTO> todayLocationList();
	public ArrayList<MainLocationDTO> todayReviewList();
}
