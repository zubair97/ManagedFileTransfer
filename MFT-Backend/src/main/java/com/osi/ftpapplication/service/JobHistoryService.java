package com.osi.ftpapplication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.osi.ftpapplication.exception.IDNotFoundException;
import com.osi.ftpapplication.model.FTPLocation;
import com.osi.ftpapplication.model.JobHistory;
import com.osi.ftpapplication.repository.JobHistoryRepository;

@Service
public class JobHistoryService {
	
	@Autowired
	JobSchedulerService jobschedularservice;
	@Autowired
	JobHistoryRepository jobHistoryRepository;
	
	public JobHistory getJobDetails(Integer id) throws IDNotFoundException {
		Optional<JobHistory> jobHistory = jobHistoryRepository.findById(id);

		if (!jobHistory.isPresent())
			throw new IDNotFoundException("id" + id);

		return jobHistory.get();
	}
	
	
	public JobHistory deleteJobDetails(Integer id) {
		jobHistoryRepository.deleteById(id);
		return null;
	}
	
	public List<JobHistory> getAllJobHistory() {
		List<JobHistory> AllJobHistory = null;
		AllJobHistory = jobHistoryRepository.findAll();
		return AllJobHistory;
	}

}
