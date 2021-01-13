/* 
 IDeleteMemberDAO.java
 - 이용자 탈퇴시 정보 개인정보 삭제
 
	1. 프로필정보에서 회원코드 확인
	2-1. 예약내역 남아있으면 탈퇴 불가
	2-2. 마일리지 남아있으면 탈퇴 불가능
	3. 환전계좌정보 테이블에서 해당 계좌번호로 이루어진 것들 삭제
	4. 충전신청계좌에서 해당계좌번호로 이루어진것들 
	   찾아서 삭제
	5. 계좌번호 테이블에서 계좌 삭제
	6. 프로필정보에서 회원코드 찾아 삭제
	7. 탈퇴회원 테이블 insert
	
*/

package com.lookation.dao;

import java.sql.SQLException;

public interface IDeleteMemberDAO
{
	// 1. 프로필정보에서 해당 이용자 있는지 확인
	public int searchMemCode(String memCode) throws SQLException;
	
	// 2. 예약내역 있는지 확인
	public int checkBook(String memCode) throws SQLException;
	
	// 마일리지 남아있는지 확인
	
	// 3. 환전계좌정보 테이블에서 해당 계좌번호로 이루어진 것들 삭제
	public int delExchangeInfo(String memCode) throws SQLException;
	
	// 4. 충전신청계좌에서 해당계좌번호 삭제
	public int delLoadReg(String memCode) throws SQLException;
	
	// 5. 계좌번호 테이블에서 계좌 삭제
	public int delBankInfo(String memCode) throws SQLException;
	
	// 6. 이용자 프로필 해당 정보 삭제
	public int delProfile(String memCode) throws SQLException;

	// 7. 탈퇴회원 테이블 insert
	public int insertDelTbl(String memCode) throws SQLException;
	
	
	
}
