package com.lookation.dao;




import com.lookation.dto.BookApplyDTO;


public interface IBookApplyDAO
{
	public BookApplyDTO bookapplyBasic(String loc_code);
	
	public BookApplyDTO bookapplyBizInfo(String loc_code);
	
	public BookApplyDTO bookapplyCaution(String loc_code);
	
	public BookApplyDTO bookapplyPackage(String loc_code, String package_appply_date);
	
	public int mileageCheck(String member_code);
	
	public int insertBookList(String member_code);
	
	public String bookCheck(String member_code);
	
	public BookApplyDTO actualBooker(String book_code);
	
	public int insertBookPay(BookApplyDTO dto);

	
}
