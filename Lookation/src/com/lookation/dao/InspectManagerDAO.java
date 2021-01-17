package com.lookation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.lookation.dto.InspectLocationDTO;

public class InspectManagerDAO implements IInspectManagerDAO
{
	private DataSource dataSource;
	
	// setter 메소드 구성
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	
	//InspectManager 화면 검수신청 리스트 출력 메소드
	@Override
	public ArrayList<InspectLocationDTO> list() throws SQLException
	{
		ArrayList<InspectLocationDTO> result = new ArrayList<InspectLocationDTO>();
		
		Connection conn = dataSource.getConnection();
		
		String sql = "SELECT INSPECT_REG_CODE,LOC_NAME,LOC_TYPE " 
					+ " FROM ADMIN_INSPECT_LIST_VIEW ";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			InspectLocationDTO dto = new InspectLocationDTO();
			dto.setInspect_reg_code(rs.getString("INSPECT_REG_CODE"));
			dto.setLoc_name(rs.getString("LOC_NAME"));
			dto.setLoc_type(rs.getString("LOC_TYPE"));
			
			result.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	//검수처리 버튼 클릭 시 검수처리 출력 메소드
	@Override
	public InspectLocationDTO prlist(String inspect_reg_code) throws SQLException
	{
		InspectLocationDTO result = new InspectLocationDTO();
		
		Connection conn = dataSource.getConnection();
		
		String sql = "SELECT INSPECT_REG_CODE, LOC_NAME, LOC_SHORT_INTRO, LOC_INTRO,"
				+ " LOC_ADDR, LOC_DETAIL_ADDR, LOC_TYPE, "
				+ " MIN_PEOPLE, MAX_PEOPLE, LOC_TEL, LOC_MAIN_TEL, LOC_EMAIL," 
				+ " BIZ_NAME, BIZ_CEO, BIZ_CEO_TYPE, BIZ_MAIN_TYPE, BIZ_SUB_TYPE,"
				+ " BIZ_ADDR, BIZ_LICENSE_NUMBER,"
				+ " LOC_USE_HOUR, LOC_USE_DAY_OFF, LOC_USE_APPOINT_DAY_OFF"
				+ " FROM ADMIN_INSPECT_PR_VIEW " 
				+ " WHERE INSPECT_REG_CODE= ? ";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, inspect_reg_code);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next())
		{
			result.setInspect_reg_code(rs.getString("INSPECT_REG_CODE"));
			result.setLoc_name(rs.getString("LOC_NAME"));
			result.setLoc_short_intro(rs.getString("LOC_SHORT_INTRO"));
			result.setLoc_intro(rs.getString("LOC_INTRO"));
			result.setLoc_addr(rs.getString("LOC_ADDR"));
			result.setLoc_detail_addr(rs.getString("LOC_DETAIL_ADDR"));
			result.setLoc_type(rs.getString("LOC_TYPE"));
			result.setMin_people(rs.getString("MIN_PEOPLE"));
			result.setMax_people(rs.getString("MAX_PEOPLE"));
			result.setLoc_tel(rs.getString("LOC_TEL"));
			result.setLoc_main_tel(rs.getString("LOC_MAIN_TEL"));
			result.setLoc_email(rs.getString("LOC_EMAIL"));
			result.setBiz_name(rs.getString("BIZ_NAME"));
			result.setBiz_ceo(rs.getString("BIZ_CEO"));
			result.setBiz_ceo_type(rs.getString("BIZ_CEO_TYPE"));
			result.setBiz_main_type(rs.getString("BIZ_MAIN_TYPE"));
			result.setBiz_sub_type(rs.getString("BIZ_SUB_TYPE"));
			result.setBiz_addr(rs.getString("BIZ_ADDR"));
			result.setBiz_license_number(rs.getString("BIZ_LICENSE_NUMBER"));
			result.setLoc_use_hour(rs.getString("LOC_USE_HOUR"));
			result.setLoc_use_day_off(rs.getString("LOC_USE_DAY_OFF"));
			result.setLoc_use_appoint_day_off(rs.getString("LOC_USE_APPOINT_DAY_OFF"));
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	 //패키지
	
	  @Override
	  public InspectLocationDTO pakageInfo(String inspect_reg_code) throws SQLException 
	  { 
		  InspectLocationDTO result = new InspectLocationDTO();
	  
	  Connection conn = dataSource.getConnection();
	  
	  String sql = "SELECT PACKAGE_NAME, PACKAGE_START,PACKAGE_END " 
			  		+ " FROM INSPECT_PACKAGE_VIEW " 
			  		+ " WHERE INSPECT_REG_CODE = ? ";
	  
	  PreparedStatement pstmt = conn.prepareStatement(sql); 
	  pstmt.setString(1,inspect_reg_code); 
	  ResultSet rs = pstmt.executeQuery();
	  
	  while (rs.next()) 
	  { 
		  result.setPackage_name(rs.getString("PACKAGE_NAME"));
		  result.setPackage_start(rs.getString("PACKAGE_START"));
		  result.setPackage_end(rs.getString("PACKAGE_END"));
	  
	  } 
	  
	  rs.close(); 
	  pstmt.close(); 
	  conn.close();
	  
	  return result; 
	 }
	
	  //시설안내
	  @Override
	   public InspectLocationDTO facilityInfo(String inspect_reg_code) throws SQLException
	   {
		  InspectLocationDTO result = new InspectLocationDTO();
	      
	      Connection conn = dataSource.getConnection();
	      
	      String sql = "SELECT FACILITY_CONTENT FROM FACILITY_INFO_VIEW "
	               + " WHERE INSPECT_REG_CODE = ? ";
	      
	      PreparedStatement pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1,inspect_reg_code);
	      ResultSet rs = pstmt.executeQuery();
	      
	      while(rs.next())
	      {
	         result.setFacility_content(rs.getString("FACILITY_CONTENT"));
	      }
	      
	      rs.close();
	      pstmt.close();
	      conn.close();
	      
	      return result;
	   }

	  //주의사항
	   @Override
	   public InspectLocationDTO cautionInfo(String inspect_reg_code) throws SQLException
	   {
		  InspectLocationDTO result = new InspectLocationDTO();
	      
	      Connection conn = dataSource.getConnection();
	      
	      String sql = "SELECT CAUTION_CONTENT FROM CAUTION_VIEW "
	               + " WHERE INSPECT_REG_CODE = ? ";
	      
	      PreparedStatement pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1,inspect_reg_code);
	      ResultSet rs = pstmt.executeQuery();
	      
	      while(rs.next())
	      {
	         result.setCaution_content(rs.getString("CAUTION_CONTENT"));
	      }
	      
	      rs.close();
	      pstmt.close();
	      conn.close();
	      
	      return result;
	   }

	   
	   /*
	   public ArrayList<LocationDTO> cautionInfo(String inspect_reg_code) throws SQLException
	   {
	      ArrayList<LocationDTO> result = new ArrayList<LocationDTO>();
	      
	      Connection conn = dataSource.getConnection();
	      
	      String sql = "SELECT CAUTION_CONTENT FROM CAUTION_CONTENT_VIEW"
	               + " WHERE INSPECT_REG_CODE=?";
	      
	      PreparedStatement pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, inspect_reg_code);
	      
	      ResultSet rs = pstmt.executeQuery();
	      
	      while(rs.next())
	      {
	         LocationDTO dto = new LocationDTO();
	         
	         dto.setCaution_content(rs.getString("CAUTION_CONTENT"));
	         
	         result.add(dto);
	      }
	      rs.close();
	      pstmt.close();
	      conn.close();
	      
	      return result;
	   }
	   */
	   
	   //사이트 
	   @Override
	   public InspectLocationDTO urlInfo(String inspect_reg_code) throws SQLException
	   {
		  InspectLocationDTO result = new InspectLocationDTO();
	      
	      Connection conn = dataSource.getConnection();
	      
	      String sql = "SELECT LOC_WEB_URL FROM WEB_VIEW "
	               + " WHERE INSPECT_REG_CODE = ? ";
	      
	      PreparedStatement pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1,inspect_reg_code);
	      ResultSet rs = pstmt.executeQuery();
	      
	      while(rs.next())
	      {
	         result.setLoc_web_url(rs.getString("LOC_WEB_URL"));
	      }
	      
	      rs.close();
	      pstmt.close(); 
	      conn.close();
	      
	      return result;
	   }
	   
	  
	//검수 승인
	//검수 반려
}
