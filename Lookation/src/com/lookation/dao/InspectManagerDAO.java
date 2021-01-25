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
		
		String sql = "SELECT INSPECT_REG_CODE,LOC_NAME,LOC_TYPE,COUNT " 
					+ " FROM ADMIN_INSPECT_LIST_VIEW"
					+ " WHERE COUNT < 1";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery(); 
		
		while(rs.next())
		{
			InspectLocationDTO dto = new InspectLocationDTO();
			dto.setInspect_reg_code(rs.getString("INSPECT_REG_CODE"));
			dto.setLoc_name(rs.getString("LOC_NAME"));
			dto.setLoc_type(rs.getString("LOC_TYPE"));
			dto.setCount(rs.getString("COUNT"));
			
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
	/*
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
	*/
	  
	/*
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
	  */
	
	//시설안내
	  @Override
	   public ArrayList<InspectLocationDTO> facilityInfo(String locCode) throws SQLException
	   {
	      ArrayList<InspectLocationDTO> result = new ArrayList<InspectLocationDTO>();
	      
	      Connection conn = dataSource.getConnection();
	      String sql = "SELECT FACILITY_CONTENT FROM FACILITY_INFO_VIEW "
	               + " WHERE INSPECT_REG_CODE = ? ";
	      
	      PreparedStatement pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, locCode);
	      
	      ResultSet rs = pstmt.executeQuery();
	      
	      while(rs.next())
	      {
	    	  InspectLocationDTO dto = new InspectLocationDTO();
	         dto.setFacility_content(rs.getString("FACILITY_CONTENT"));
	         
	         result.add(dto);
	      }
	      rs.close();
	      pstmt.close();
	      conn.close();
	      
	      return result;
	   }


	  
	  /*
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
*/
	//주의사항
	   @Override
	   public ArrayList<InspectLocationDTO> cautionInfo(String inspect_reg_code) throws SQLException
	   {
	      ArrayList<InspectLocationDTO> result = new ArrayList<InspectLocationDTO>();
	      
	      Connection conn = dataSource.getConnection();
	      
	      String sql = "SELECT CAUTION_CONTENT FROM CAUTION_VIEW "
	               + " WHERE INSPECT_REG_CODE = ? ";
	      
	      PreparedStatement pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1, inspect_reg_code);
	      
	      ResultSet rs = pstmt.executeQuery();
	      
	      while(rs.next())
	      {
	    	  InspectLocationDTO dto = new InspectLocationDTO();
	         
	         dto.setCaution_content(rs.getString("CAUTION_CONTENT"));
	         
	         result.add(dto);
	      }
	      rs.close();
	      pstmt.close();
	      conn.close();
	      
	      return result;
	   }
	  
	   
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
	   
	   //공간 이미지
	   @Override
	   public ArrayList<String> loc_img(String inspect_reg_code) throws SQLException
	   {
		   ArrayList<String> result = new ArrayList<String>();
	      
	      Connection conn = dataSource.getConnection();
	      
	      String sql = "SELECT LOC_DETAIL_IMG_URL FROM LOC_IMG_VIEW "
	               + " WHERE INSPECT_REG_CODE = ? ";
	      
	      PreparedStatement pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1,inspect_reg_code);
	      ResultSet rs = pstmt.executeQuery();
	      
	      while(rs.next())
	      {
	         result.add(rs.getString("LOC_DETAIL_IMG_URL"));
	      }
	      
	      rs.close();
	      pstmt.close(); 
	      conn.close();
	      
	      return result;
	   }
	   
	   //사업자 등록증
	   @Override
	   public InspectLocationDTO biz_img(String inspect_reg_code) throws SQLException
	   {
		  InspectLocationDTO result = new InspectLocationDTO();
	      
	      Connection conn = dataSource.getConnection();
	      
	      String sql = "SELECT BIZ_LICENSE_URL FROM BIZ_IMG_VIEW "
	               + " WHERE INSPECT_REG_CODE = ? ";
	      
	      PreparedStatement pstmt = conn.prepareStatement(sql);
	      pstmt.setString(1,inspect_reg_code);
	      ResultSet rs = pstmt.executeQuery();
	      
	      while(rs.next())
	      {
	         result.setBiz_license_url(rs.getString("BIZ_LICENSE_URL"));
	      }
	      
	      rs.close();
	      pstmt.close(); 
	      conn.close();
	      
	      return result;
	   }
	   
	   	// 여기서 매개변수 inspect_reg_code 아니라 여기관련 dto 받아서
	   // dto.getXXX 로 세개 맞게 세팅해줘야하고
	   // inspect_type_code는 완료/반려에 맞게 입력하니까 ?가 아니라 --> 저걸 지우고여기서
	   // 숫자 입력해도 될 것 같고
	   // inspect_proc_reason은 이전 폼에서 받고
	   // inspect_reg_code는 select문 써서(이거 찾는 메소드 하나 더 추가) 받아와서 넣어야할듯...
	   
		 //검수처리(완료) 
		@Override
		public int Inspect(InspectLocationDTO dto) throws SQLException
		{
			int result = 0;
			
			Connection conn = dataSource.getConnection();
			
			
			String sql = "INSERT INTO INSPECT_PROC_LIST (INSPECT_PROC_CODE"
						+ ", INSPECT_REG_CODE, INSPECT_TYPE_CODE" 
						+ ", INSPECT_PROC_REASON ,INSPECT_PROC_DATE)" 
						+ " VALUES (F_CODE('IP', IP_SEQ.NEXTVAL), ?, ?, ?, SYSDATE)"; 
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getInspect_reg_code());
			pstmt.setString(2, dto.getInspect_type_code());
			pstmt.setString(3, dto.getInspect_proc_reason());
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			conn.close();
			
			return result;
		}

}
