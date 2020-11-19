package com.osi.ftpapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osi.ftpapplication.model.FTPLocation;

@Repository
public interface FTPLocationRepository extends JpaRepository<FTPLocation, Integer> {
	
	FTPLocation getByLocationName(String locationName);
	
	
}
