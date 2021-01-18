package com.lookation.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.lookation.dto.reportDTO;

public interface IReportDAO
{
	// 예약 신고 인터페이스
	
	public ArrayList<reportDTO> bookBasicList();
	// 상세보기 (신고처리 전)
	public reportDTO bookDetailList0(String book_report_code);
	// 상세보기 (신고처리 후)
	public reportDTO bookDetailList1(String book_report_code);
	// 신고처리 테이블 추가 쿼리문
	public int bookReportProc(@Param("book_report_code") String book_report_code, @Param("report_proc_type_code") String report_proc_type_code);
	// 블랙리스트 테이블 추가 쿼리문
	public int userBlackList(@Param("member_email") String member_email, @Param("member_blacklist_reason") String member_blacklist_reason);
	
	// 공간 신고 인터페이스
	
	public ArrayList<reportDTO> locBasicList();
	// 상세보기 (신고처리 전)
	public reportDTO locDetailList0(String loc_report_code);
	// 상세보기 (신고처리 후)
	public reportDTO locDetailList1(String loc_report_code);
	// 신고처리 테이블 추가 쿼리문
	public int locReportProc(@Param("loc_report_code") String loc_report_code, @Param("report_proc_type_code") String report_proc_type_code);
	//블랙리스트 테이블 추가 쿼리문
	public int hostBlackList(@Param("host_email") String host_email,@Param("host_blacklist_reason") String host_blacklist_reason);
}
