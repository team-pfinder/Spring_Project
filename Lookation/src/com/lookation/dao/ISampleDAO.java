package com.lookation.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.lookation.dto.SampleDTO;

public interface ISampleDAO
{
	// 오버라이딩 할 가상함수를 입력
	public ArrayList<SampleDTO> lists() throws SQLException;
}
