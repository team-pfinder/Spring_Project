package com.lookation.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.lookation.dto.InspectLocationDTO;

public interface IInspectManagerDAO
{
	//검수 신청
	public ArrayList<InspectLocationDTO> list() throws SQLException;
	
	//검수신청항목
	public InspectLocationDTO prlist(String inspect_reg_code) throws SQLException;
	
	//패키지
	//public InspectLocationDTO pakageInfo(String inspect_reg_code) throws SQLException;
	
	//시설안내
	//public InspectLocationDTO facilityInfo(String inspect_reg_code) throws SQLException;
	public ArrayList<InspectLocationDTO> facilityInfo(String locCode) throws SQLException;
	
	//주의사항
	//public InspectLocationDTO cautionInfo(String inspect_reg_code) throws SQLException;
	public ArrayList<InspectLocationDTO> cautionInfo(String inspect_reg_code) throws SQLException;
	
	//사이트 
	public InspectLocationDTO urlInfo(String inspect_reg_code) throws SQLException;
	
	//이미지
	public ArrayList<String> loc_img(String inspect_reg_code) throws SQLException;
	
	//사업자 등록증
	public InspectLocationDTO biz_img(String inspect_reg_code) throws SQLException;
	
	//검수처리
	public int Inspect(InspectLocationDTO dto) throws SQLException;
	
}
