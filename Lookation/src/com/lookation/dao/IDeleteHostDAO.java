/* 
 IDeleteHostDAO.java
 - 호스트 탈퇴시 개인정보 삭제
 
	1. 탈퇴내역에 해당 호스트 있는지 확인
	2. 예약내역 있으면 탈퇴 불가능
	3. 마일리지 남아있으면 탈퇴 불가능 
	4. LOC_CONTACT에서 연락처 삭제
	5. BIZ_INFO에서 사업자정보 삭제
	6. LOC_REMOVE(LRM)에 삭제된 공간으로 INSERT
	7. 환전계좌에서 해당 계좌정보 DELETE
	8. HOST_BANK_INFO에서 해당 회원코드의 계좌정보 DELETE
	9. HOST_PROFILE에서 해당 회원코드인 회원정보 DELETE
	10. HOST_WITHDRAW(HW) 에 해당 회원코드 회원 INSERT

*/

package com.lookation.dao;

import java.sql.SQLException;

public interface IDeleteHostDAO
{	
	// 1. 탈퇴내역에 해당 호스트 있는지 확인
	public int searchHostCode(String hostCode) throws SQLException;
	
	// 2. 예약내역 있으면 탈퇴 불가능
	public int checkBook(String hostCode) throws SQLException;
	
	// 마일리지 남아있는지 확인
	
	// 4. LOC_CONTACT에서 연락처 삭제
	public int delContact(String hostCode) throws SQLException;
		
	// 5. BIZ_INFO에서 사업자정보 삭제
	public int delBizInfo(String hostCode) throws SQLException;
		
	// 6 LOC_REMOVE(LRM)에 해당 공간 코드들을 삭제된 공간으로 INSERT
	public int insertLocRemove(String hostCode) throws SQLException;
	
	// 7. 환전계좌에서 해당 계좌정보 DELETE
	public int delExchangeInfo(String hostCode) throws SQLException;
	
	// 8. HOST_BANK_INFO에서 해당 회원코드의 계좌정보 DELETE
	public int delBankInfo(String hostCode) throws SQLException;
	
	// 9. HOST_PROFILE에서 해당 회원코드인 회원정보 DELETE
	public int delProfile(String hostCode) throws SQLException;

	// 10. HOST_WITHDRAW(HW) 에 해당 회원코드 회원 INSERT
	public int insertDelHost(String hostCode) throws SQLException;
	
	
	
}
