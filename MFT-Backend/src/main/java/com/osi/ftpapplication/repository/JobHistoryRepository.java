package com.osi.ftpapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.osi.ftpapplication.model.JobHistory;

@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistory, Integer> {

	
	
	
}
