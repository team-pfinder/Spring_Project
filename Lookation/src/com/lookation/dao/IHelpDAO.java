package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.HelpDTO;

public interface IHelpDAO
{
	//도움말 목록 조회
	public ArrayList<HelpDTO> U_helpList();
	//도움말 클릭시 내용 조회
	public HelpDTO U_detailList(String help_code);
}
