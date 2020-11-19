package com.osi.ftpapplication.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jcraft.jsch.SftpException;
import com.osi.ftpapplication.exception.JobSchedulerNotFoundException;
import com.osi.ftpapplication.model.JobSchedular;
import com.osi.ftpapplication.service.JobSchedulerService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/jobScheduler")
public class JobSchedulerController {

	@Autowired
	JobSchedulerService jobSchedulerService;

	/**
	 * Create JobScheduler
	 * 
	 * @param jobsScheduler
	 * @return created JobScheduler successfully
	 */
	/// From UI, PostMan
	@PostMapping("/createJobScheduler")
	public String createJobScheduler(@RequestBody JobSchedular jobsSchedular) {
		return jobSchedulerService.createJobScheduler(jobsSchedular);
	}

	/**
	 * Update JobScheduler by ID
	 * 
	 * @param id
	 * @param jobScheduler
	 * @return Updated JobScheduler
	 * @throws SftpException
	 * @throws IOException
	 * @throws JobSchedulerNotFoundException
	 */
	// from UI,postMan
	@GetMapping("/runschedular/{id}")
	public boolean runSchedular(@PathVariable Integer id) throws IOException, SftpException {
		jobSchedulerService.runJobSchedular(id);
		return true;
	}

	/// From UI, PostMan
	@PutMapping("/updateJobScheduler/{id}")
	public JobSchedular updateJobScheduler(@PathVariable Integer id, @RequestBody JobSchedular jobScheduler)
			throws JobSchedulerNotFoundException {
		return jobSchedulerService.updateJobScheduler(id, jobScheduler);
	}

	/**
	 * Get JobScheduler by name
	 * 
	 * @param name
	 * @return Successfully returned JobScheduler with name
	 */
	/// From UI, PostMan
	@GetMapping("/getJobScheduler/{name}")
	public JobSchedular getJobScheduler(@PathVariable String name) {
		return jobSchedulerService.getJobScheduler(name);
	}

	/**
	 * Get JobScheduler by id
	 * 
	 * @param id
	 * @return Successfully returned JobScheduler with id
	 */
	@GetMapping("/getJobScheduler/{id}")
	public JobSchedular getJobSchedular(@PathVariable Integer id) {
		return jobSchedulerService.getJobSchedular(id);

	}

	/**
	 * Get all created JobSchedulers
	 * 
	 * @return All JobSchedulers
	 */
	/// From UI, PostMan
	@GetMapping("/getAllJobSchedulers")
	public List<JobSchedular> getAllJobSchedulers() {
		return jobSchedulerService.getAllJobSchedulers();
	}

	/**
	 * Delete JobScheduler by ID
	 * 
	 * @param id
	 * @return The JobScheduler has been successfully deleted
	 */
	@DeleteMapping("/deleteJobScheduler/{id}")
	public JobSchedular deleteJobScheduler(@PathVariable Integer id) {
		return jobSchedulerService.deleteJobScheduler(id);
	}
	
	@GetMapping("/startSchedulers")
	public String startSchedulers()  {
		return jobSchedulerService.startSchedulers();
	}

}
