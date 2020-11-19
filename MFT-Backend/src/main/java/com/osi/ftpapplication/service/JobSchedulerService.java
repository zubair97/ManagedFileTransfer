package com.osi.ftpapplication.service;

import org.springframework.stereotype.Service;

import com.jcraft.jsch.SftpException;
import com.osi.ftpapplication.exception.JobSchedulerNotFoundException;
import com.osi.ftpapplication.model.JobHistory;
import com.osi.ftpapplication.model.JobSchedular;
import com.osi.ftpapplication.repository.JobHistoryRepository;
import com.osi.ftpapplication.repository.JobSchedulerRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

@Service
public class JobSchedulerService {
	
	/*
	 * JobSchedulerService(){ super(); startSchedulers(); }
	 */
	@Autowired
	FTPLocationService ftpservice;

	@Autowired
	JobHistoryRepository jobhistoryrepository;

	@Autowired
	JobSchedulerRepository jobSchedulerRepository;

	public String createJobScheduler(JobSchedular jobsScheduler) {
		JobSchedular savedJobScheduler = jobSchedulerRepository.save(jobsScheduler);

		if (savedJobScheduler != null)
			return "Your Job Scheduler is saved Successfully" + jobsScheduler.getSchedulerName();
		else
			return "Your Job Scheduler isn't saved " + jobsScheduler.getSchedulerName();

	}

	public JobSchedular getJobScheduler(String schedulerName) {

		return jobSchedulerRepository.findBySchedulerName(schedulerName);

	}

	public JobSchedular getJobSchedular(Integer id) {
		// TODO Auto-generated method stub
		return jobSchedulerRepository.getOne(id);
	}

	public JobSchedular updateJobScheduler(Integer id, JobSchedular jobScheduler) throws JobSchedulerNotFoundException {
		if (!jobSchedulerRepository.existsById(id)) {
			throw new JobSchedulerNotFoundException("Job Scheduler does not exist" + id);
		}
		JobSchedular updatedJobScheduler = new JobSchedular();
		updatedJobScheduler.setId(id);
		updatedJobScheduler.setSchedulerName(jobScheduler.getSchedulerName());
		updatedJobScheduler.setSourceLocationID(jobScheduler.getSourceLocationID());
		updatedJobScheduler.setDestinationLocationID(jobScheduler.getDestinationLocationID());
		updatedJobScheduler.setFrequency(jobScheduler.getFrequency());
		updatedJobScheduler.setDatetime(jobScheduler.getDatetime());
		return jobSchedulerRepository.save(updatedJobScheduler);
	}

	public List<JobSchedular> getAllJobSchedulers() {
		List<JobSchedular> AllJobScheduler = null;
		AllJobScheduler = jobSchedulerRepository.findAll();
		return AllJobScheduler;
	}

	public JobSchedular deleteJobScheduler(Integer id) {
		jobSchedulerRepository.deleteById(id);
		return null;
	}

	@PostConstruct
	public String startSchedulers() {
		ThreadPoolTaskScheduler tpts = new ThreadPoolTaskScheduler();
		tpts.setPoolSize(10);
		tpts.initialize();
		
		// get list of schedulers/
		    List<JobSchedular> sch = getAllJobSchedulers();
		    for( JobSchedular jsc:sch) {
			LocalDateTime localDateTime = jsc.getDatetime();
			int hours = localDateTime.getHour();					
			int mins=localDateTime.getMinute();
	/*		String hours = "*";				
			String mins="0/1";
	*/		String cronJob = "0 "+mins+" "+hours+" * * ?";
			
		    System.out.println("Starting scheduler "+jsc.getId());
			
			tpts.schedule(new RunnableTask(jsc.getId()), new CronTrigger(cronJob));
		    }
		
		return "job triggered";
	}

    class RunnableTask implements Runnable{
	    private Integer schedulerId;
	   
	    public RunnableTask(Integer schedulerId) {
	        this.schedulerId = schedulerId;
	    }
	   
	    @Override
	    public void run() {
	    	runJobSchedular(schedulerId);
	    }
    }


	public boolean runJobSchedular(Integer schedulerId) {

	    System.out.println("Running scheduler "+schedulerId);
	    boolean ret_val = false;

		JobSchedular savedJobScheduler = jobSchedulerRepository.getOne(schedulerId);

		Integer sourceID = savedJobScheduler.getSourceLocationID();
		Integer destID = savedJobScheduler.getDestinationLocationID();

		try {

			ret_val = copyFiles(schedulerId, sourceID, destID);
		} catch (Exception e) {
			System.out.println("Scheduler Exception : "+e.toString());

		}
		System.out.println("Scheduler status : "+ret_val);
		return ret_val;

	}

	private boolean copyFiles(Integer schedulerId, Integer sourceID, Integer destID) throws IOException, SftpException {
		LocalDateTime localdatetime1 = LocalDateTime.now();
		boolean ret_val = false;

		JobHistory jobhistory = new JobHistory();
		jobhistory.setSchedulerid(schedulerId);
		jobhistory.setDatetime(localdatetime1);
		if (ftpservice.copyFiles(sourceID, destID)) {
			jobhistory.setStatus("success");
			jobhistory.setDescription("Server is Up and Running.");
			ret_val = true;
		} else {
			jobhistory.setStatus("failed");
			jobhistory.setDescription("Server is down due to Network issue.");
		}
		jobhistoryrepository.save(jobhistory);
		return ret_val;
	}

}
