package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.AdminHostDTO;

public interface IhostReviewManagerDAO
{
	public ArrayList<AdminHostDTO> r_firstList();
	public AdminHostDTO r_secondList(String review_reply_code);
	public int r_remove(String review_reply_code);

	
}