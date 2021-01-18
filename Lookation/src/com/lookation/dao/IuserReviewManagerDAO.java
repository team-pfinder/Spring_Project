package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.adminUserDTO;

public interface IuserReviewManagerDAO
{
	public ArrayList<adminUserDTO> basicList();
	public adminUserDTO detailList(String review_code);
	public int remove(String review_code);
}
