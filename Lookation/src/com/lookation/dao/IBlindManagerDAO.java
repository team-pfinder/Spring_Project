package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.blindManagerDTO;

public interface IBlindManagerDAO
{
	public ArrayList<blindManagerDTO> list();
	public int remove(String loc_blind_code);
}
