package com.osi.ftpapplication.repository;

import org.springframework.stereotype.Repository;

import com.osi.ftpapplication.model.JobSchedular;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface JobSchedulerRepository extends JpaRepository<JobSchedular, Integer>{
	
	JobSchedular findBySchedulerName(String schedulerName);
	
	
   // void runJobSchedularById(Integer id,String sourceLocationName,String destinationLocationName);
   
   
	}