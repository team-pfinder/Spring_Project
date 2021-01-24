package com.lookation.dao;


import java.sql.SQLException;
import java.util.ArrayList;

import com.lookation.dto.LocationDetailDTO;

public interface ILocationDetailDAO
{
	public LocationDetailDTO basicInfo(String locCode) throws SQLException;
	
	public LocationDetailDTO usingInfo(String locCode) throws SQLException;
	
	public ArrayList<LocationDetailDTO> packageInfo(String locCode, String date) throws SQLException;
	
	public ArrayList<LocationDetailDTO> facilityInfo(String locCode) throws SQLException;
	
	public ArrayList<LocationDetailDTO> cautionInfo(String locCode) throws SQLException;
	
	public LocationDetailDTO bizInfo(String locCode) throws SQLException;
	
	public ArrayList<LocationDetailDTO> qna(String locCode) throws SQLException;
	
	public int countQna(String locCode) throws SQLException;
	
	public ArrayList<LocationDetailDTO> review(String locCode) throws SQLException;
	
	public int countReview(String locCode) throws SQLException;
	
	public String avgReviewRate(String locCode) throws SQLException;
	
	public ArrayList<LocationDetailDTO> viewPhoto() throws Exception;
	
	public ArrayList<String> detailPhoto(String locCode) throws Exception;
}