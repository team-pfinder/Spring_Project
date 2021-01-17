package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.AdminHostDTO;

public interface IhostQnaManagerDAO
{
	public ArrayList<AdminHostDTO> firstList();
	public AdminHostDTO secondList(String qna_reply_code);
	public int remove(String qna_reply_code);
	
}