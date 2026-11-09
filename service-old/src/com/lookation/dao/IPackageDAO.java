package com.lookation.dao;

import java.util.ArrayList;

import com.lookation.dto.PackageDTO;

public interface IPackageDAO
{
	public ArrayList<PackageDTO> packageFormList(String locationCode);
	public ArrayList<PackageDTO> packageApplyList(String locationCode);
	public ArrayList<PackageDTO> packageApplyBookList(String locationCode);
	
	
	public int countApply(PackageDTO pack);
	public void insertApply(PackageDTO pack);
	public void updateApply(PackageDTO pack);
	public void deleteApply(PackageDTO pack);
	
	//
	public void insertPackage(PackageDTO pack);
	public void deletePackage(PackageDTO pack);
}
