package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.adminUserDTO;

public interface IuserQnaManagerDAO
{
	public ArrayList<adminUserDTO> basicList();
	public adminUserDTO detailList(String qna_code);
	public int remove(String qna_code);
}