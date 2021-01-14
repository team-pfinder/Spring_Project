package com.lookation.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.lookation.dto.adminUserDTO;

public interface IuserQnaManager
{
	public ArrayList<adminUserDTO> basicList() throws SQLException;
	public adminUserDTO detailList(String qnaCode) throws SQLException;
	public int remove(adminUserDTO dto) throws SQLException;
	
	// 추가
	// public int removeCheck() throws SQLException;
}
