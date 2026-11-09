package com.lookation.dao;

import org.apache.ibatis.annotations.Param;

import com.lookation.dto.reportUserDTO;

public interface IReportUserDAO
{
	public int add(reportUserDTO dto);
	public int addImg(@Param("loc_report_code")String loc_report_code, @Param("loc_report_img_url")String loc_report_img_url);
	public reportUserDTO list(@Param("loc_code")String loc_code, @Param("member_code")String member_code);
}
