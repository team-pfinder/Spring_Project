package com.lookation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import com.lookation.dto.LocationDetailDTO;

public class LocationDetailDAO implements ILocationDetailDAO
{
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource)
	{
		this.dataSource = dataSource;
	}


	@Override
	public LocationDetailDTO basicInfo(String locCode) throws SQLException
	{
		LocationDetailDTO result = new LocationDetailDTO();
		Connection conn = dataSource.getConnection();
		
		String sql = "SELECT LOC_NAME, LOC_TYPE, LOC_SHORT_INTRO, LOC_INTRO"
				   + ", LOC_ADDR, LOC_DETAIL_ADDR, MIN_PEOPLE, MAX_PEOPLE"
				   + ", LOC_REG_DATE, HOST_NICKNAME, LOC_CODE, HOST_CODE"
			   	   + ", LOC_WEB_URL"
				   + " FROM VIEW_BASIC_INFO" 
				   + " WHERE LOC_CODE = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, locCode);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			result.setLocName(rs.getString("LOC_NAME"));
			result.setLocType(rs.getString("LOC_TYPE"));
			result.setShortIntro(rs.getString("LOC_SHORT_INTRO"));
			result.setIntro(rs.getString("LOC_INTRO"));
			result.setAddr(rs.getString("LOC_ADDR"));
			result.setDetailAddr(rs.getString("LOC_DETAIL_ADDR"));
			result.setMinPeople(rs.getInt("MIN_PEOPLE"));
			result.setMaxPeople(rs.getInt("MAX_PEOPLE"));
			result.setLocRegDate(rs.getString("LOC_REG_DATE"));
			result.setHostNickName(rs.getString("HOST_NICKNAME"));
			result.setLocationCode(rs.getString("LOC_CODE"));
			result.setHostCode(rs.getString("HOST_CODE"));
			result.setUrl(rs.getString("LOC_WEB_URL"));
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	@Override
	public LocationDetailDTO usingInfo(String locCode) throws SQLException
	{
		LocationDetailDTO result = new LocationDetailDTO();
		Connection conn = dataSource.getConnection();
		
		String sql = "SELECT LOC_USE_HOUR, LOC_USE_DAY_OFF, LOC_USE_APPOINT_DAY_OFF"
				   + ", LOC_CODE FROM VIEW_USING_HOUR"
				   + " WHERE LOC_CODE=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, locCode);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			result.setUsingHour(rs.getString("LOC_USE_HOUR"));
			result.setDayOff(rs.getString("LOC_USE_DAY_OFF"));
			result.setAppointDayOff(rs.getString("LOC_USE_APPOINT_DAY_OFF"));
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	
	// 해당 날짜에 있는 패키지 정보 가져옴
	@Override
	public ArrayList<LocationDetailDTO> packageInfo(String locCode, String date) throws SQLException
	{	
		
		ArrayList<LocationDetailDTO> result = new ArrayList<LocationDetailDTO>();
		
		Connection conn = dataSource.getConnection();
		String sql = "SELECT PACKAGE_NAME, PACKAGE_START"
				   + ", PACKAGE_END, PACKAGE_PRICE, APPLY_DATE"
				   + ", APPLY_PACKAGE_CODE"
				   + " FROM VIEW_APPLY_PACKAGE_INFO"
				   + " WHERE LOC_CODE = ? AND APPLY_DATE=? AND COUNT=0";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, locCode);
		pstmt.setString(2, date);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			LocationDetailDTO dto = new LocationDetailDTO();
			
			dto.setPackageName(rs.getString("PACKAGE_NAME"));
			dto.setPackStart(rs.getInt("PACKAGE_START"));
			dto.setPackEnd(rs.getInt("PACKAGE_END"));
			dto.setPackPrice(rs.getInt("PACKAGE_PRICE"));
			dto.setApplyDate(rs.getString("APPLY_DATE"));
			dto.setApply_package_code(rs.getString("APPLY_PACKAGE_CODE"));
			
			result.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}


	@Override
	public ArrayList<LocationDetailDTO> facilityInfo(String locCode) throws SQLException
	{
		ArrayList<LocationDetailDTO> result = new ArrayList<LocationDetailDTO>();
		
		Connection conn = dataSource.getConnection();
		String sql = "SELECT FACILITY_CONTENT FROM VIEW_FACILITY_INFO"
				   + " WHERE LOC_CODE=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, locCode);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			LocationDetailDTO dto = new LocationDetailDTO();
			dto.setFacility(rs.getString("FACILITY_CONTENT"));
			
			result.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}


	@Override
	public ArrayList<LocationDetailDTO> cautionInfo(String locCode) throws SQLException
	{
		ArrayList<LocationDetailDTO> result = new ArrayList<LocationDetailDTO>();
		
		Connection conn = dataSource.getConnection();
		String sql = "SELECT CAUTION_CONTENT FROM VIEW_CAUTION_CONTENT"
				   + " WHERE LOC_CODE=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, locCode);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			LocationDetailDTO dto = new LocationDetailDTO();
			
			dto.setCaution(rs.getString("CAUTION_CONTENT"));
			
			result.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 사업자 정보
	@Override
	public LocationDetailDTO bizInfo(String locCode) throws SQLException
	{
		LocationDetailDTO result = new LocationDetailDTO();
		Connection conn = dataSource.getConnection();
		
		String sql = "SELECT BIZ_NAME, BIZ_CEO, BIZ_CEO_TYPE"
				   + ", BIZ_MAIN_TYPE, BIZ_SUB_TYPE, BIZ_LICENSE_NUMBER"
				   + " FROM BIZ_INFO" 
				   + " WHERE LOC_CODE = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, locCode);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			result.setBizName(rs.getString("BIZ_NAME"));
			result.setBizCeo(rs.getString("BIZ_CEO"));
			result.setBizCeoType(rs.getString("BIZ_CEO_TYPE"));
			result.setBizMainType(rs.getString("BIZ_MAIN_TYPE"));
			result.setBizSubType(rs.getString("BIZ_SUB_TYPE"));
			result.setBizLicenseNum(rs.getString("BIZ_LICENSE_NUMBER"));
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	// qna 출력
	@Override
	public ArrayList<LocationDetailDTO> qna(String locCode) throws SQLException
	{
		ArrayList<LocationDetailDTO> result = new ArrayList<LocationDetailDTO>();
		
		Connection conn = dataSource.getConnection();
		String sql = "SELECT QNA_CODE, MEMBER_NICKNAME, QNA_CONTENT, QNA_DATE, MEMBER_CODE" 
				   + ", REPLYCOUNT, QNAREMOVECOUNT, QNAREPLYREMOVECOUNT"
				   + ", QNA_REPLY_CONTENT, QNA_REPLY_DATE, HOST_CODE, QNA_REPLY_CODE"
				   + " FROM VIEW_QNA" 
				   + " WHERE LOC_CODE=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, locCode);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			LocationDetailDTO dto = new LocationDetailDTO();
			
			dto.setBoardCode(rs.getString("QNA_CODE"));
			dto.setMemberNickName(rs.getString("MEMBER_NICKNAME"));
			dto.setQna_content(rs.getString("QNA_CONTENT"));
			dto.setDate(rs.getString("QNA_DATE"));
			dto.setMemCode(rs.getString("MEMBER_CODE"));
			dto.setCount(rs.getString("REPLYCOUNT"));
			dto.setRemoveCount(rs.getString("QNAREMOVECOUNT"));
			dto.setReplyRemove(rs.getString("QNAREPLYREMOVECOUNT"));
			dto.setReplyContent(rs.getString("QNA_REPLY_CONTENT"));
			dto.setReplyDate(rs.getString("QNA_REPLY_DATE"));
			dto.setHostCode(rs.getString("HOST_CODE"));
			dto.setReplyCode(rs.getString("QNA_REPLY_CODE"));
			
			result.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	// QnA 갯수
	@Override
	public int countQna(String locCode) throws SQLException
	{
		int result = 0;
		
		Connection conn = dataSource.getConnection();
		String sql = "SELECT COUNT(*) AS COUNT FROM VIEW_QNA WHERE LOC_CODE=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, locCode);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			result = rs.getInt("COUNT");
		}
		
		return result;
	}


	// 리뷰 출력
	@Override
	public ArrayList<LocationDetailDTO> review(String locCode) throws SQLException
	{
		ArrayList<LocationDetailDTO> result = new ArrayList<LocationDetailDTO>();
		
		Connection conn = dataSource.getConnection();
		String sql = "SELECT REVIEW_CODE, MEMBER_NICKNAME, REVIEW_RATE"
				   + ", REVIEW_CONTENT, REVIEW_DATE"
				   + ", REVIEW_IMG_URL, RVIMGCOUNT, REPLYCOUNT, REVIEWREMOVECOUNT"
				   + ", REPLYREMOVECOUNT, MEMBER_CODE, REVIEW_REPLY_CONTENT"
				   + ", REVIEW_REPLY_DATE, HOST_CODE, REVIEW_REPLY_CODE"
				   + " FROM VIEW_REVIEW WHERE LOC_CODE=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, locCode);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			LocationDetailDTO dto = new LocationDetailDTO();
			
			dto.setBoardCode(rs.getString("REVIEW_CODE"));
			dto.setMemberNickName(rs.getString("MEMBER_NICKNAME"));
			dto.setReviewRate(rs.getString("REVIEW_RATE"));
			dto.setContent(rs.getString("REVIEW_CONTENT"));
			dto.setDate(rs.getString("REVIEW_DATE"));
			dto.setUrl(rs.getString("REVIEW_IMG_URL"));
			dto.setRvimgCount(rs.getString("RVIMGCOUNT"));
			dto.setCount(rs.getString("REPLYCOUNT"));
			dto.setRemoveCount(rs.getString("REVIEWREMOVECOUNT"));
			dto.setReplyRemove(rs.getString("REPLYREMOVECOUNT"));
			dto.setMemCode(rs.getString("MEMBER_CODE"));
			dto.setReplyContent(rs.getString("REVIEW_REPLY_CONTENT"));
			dto.setReplyDate(rs.getString("REVIEW_REPLY_DATE"));
			dto.setHostCode(rs.getString("HOST_CODE"));
			dto.setReplyCode(rs.getString("REVIEW_REPLY_CODE"));
			
			
			result.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	
	// 리뷰 갯수 
	@Override
	public int countReview(String locCode) throws SQLException
	{
		int result = 0;
		
		Connection conn = dataSource.getConnection();
		String sql = "SELECT COUNT(*) AS COUNT FROM VIEW_REVIEW WHERE LOC_CODE=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, locCode);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			result = rs.getInt("COUNT");
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 리뷰 평균 별점
	@Override
	public String avgReviewRate(String locCode) throws SQLException
	{
		String result = null;
		
		Connection conn = dataSource.getConnection();
		String sql = "SELECT ROUND(AVG(NVL(REVIEW_RATE, 0)), 2) AS AVGSTAR"
				   + " FROM REVIEW" 
				   + " WHERE LOC_CODE=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, locCode);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			result = rs.getString("AVGSTAR");
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}

	// 리뷰 사진후기만 보기
	@Override
	public ArrayList<LocationDetailDTO> viewPhoto() throws Exception
	{
		ArrayList<LocationDetailDTO> result = new ArrayList<LocationDetailDTO>();
		
		Connection conn = dataSource.getConnection();
		String sql = "SELECT REVIEW_CODE, MEMBER_NICKNAME"
				   + ", LOC_CODE, REVIEW_RATE, REVIEW_CONTENT, REVIEW_DATE" 
				   + ", REVIEW_IMG_URL, RVIMGCOUNT" 
				   + ", REVIEWREMOVECOUNT" 
				   + ", REPLYCOUNT, REPLYREMOVECOUNT" 
				   + ", MEMBER_CODE, REVIEW_REPLY_CONTENT, REVIEW_REPLY_DATE, HOST_CODE" 
				   + ", REVIEW_REPLY_CODE" 
				   + " FROM VIEW_REVIEW" 
				   + " WHERE RVIMGCOUNT>0";
	
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			LocationDetailDTO dto = new LocationDetailDTO();
			
			dto.setBoardCode(rs.getString("REVIEW_CODE"));
			dto.setMemberNickName(rs.getString("MEMBER_NICKNAME"));
			dto.setLocationCode(rs.getString("LOC_CODE"));
			dto.setReviewRate(rs.getString("REVIEW_RATE"));
			dto.setContent(rs.getString("REVIEW_CONTENT"));
			dto.setDate(rs.getString("REVIEW_DATE"));
			dto.setUrl(rs.getString("REVIEW_IMG_URL"));
			dto.setRvimgCount(rs.getString("RVIMGCOUNT"));
			dto.setCount(rs.getString("REPLYCOUNT"));
			dto.setRemoveCount(rs.getString("REVIEWREMOVECOUNT"));
			dto.setReplyRemove(rs.getString("REPLYREMOVECOUNT"));
			dto.setMemCode(rs.getString("MEMBER_CODE"));
			dto.setReplyContent(rs.getString("REVIEW_REPLY_CONTENT"));
			dto.setReplyDate(rs.getString("REVIEW_REPLY_DATE"));
			dto.setHostCode(rs.getString("HOST_CODE"));
			dto.setReplyCode(rs.getString("REVIEW_REPLY_CODE"));
			
			result.add(dto);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
