package com.lookation.dao;

import org.apache.ibatis.annotations.Param;

import com.lookation.dto.reportHostDTO;

public interface IReportHostDAO
{
	public int add(reportHostDTO dto);
	public reportHostDTO list(@Param("book_code")String book_code, @Param("host_code")String host_code);
	public int addImg(@Param("book_report_code")String book_report_code, @Param("book_report_img_url")String book_report_img_url);
}
