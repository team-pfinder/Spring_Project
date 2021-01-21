package com.lookation.dao;




import com.lookation.dto.BookApplyDTO;


public interface IBookApplyDAO
{
	public BookApplyDTO bookapplyBasic(String loc_code);
	
	public BookApplyDTO bookapplyBizInfo(String loc_code);
	
	public BookApplyDTO bookPackage(String apply_package_code);

	public BookApplyDTO setProfile(String member_code);
	
	public int mileageCheck(String member_code);
	
	public int insertBookList(BookApplyDTO dto);
	
	public int actualBooking(BookApplyDTO dto);
	
	public BookApplyDTO bookNotice(String member_code);

	
}
