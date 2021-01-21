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
	public InspectLocationDTO pakageInfo(String inspect_reg_code) throws SQLException;
	//시설안내
	public InspectLocationDTO facilityInfo(String inspect_reg_code) throws SQLException;
	//public ArrayList<LocationDTO> facilityInfo(String inspect_reg_code) throws SQLException;
	
	//주의사항
	public InspectLocationDTO cautionInfo(String inspect_reg_code) throws SQLException;
	//public ArrayList<LocationDTO> cautionInfo(String inspect_reg_code) throws SQLException;
	
	//사이트 
	public InspectLocationDTO urlInfo(String inspect_reg_code) throws SQLException;
	
	//검수처리
	public int Inspect(InspectLocationDTO dto) throws SQLException;
	
}
