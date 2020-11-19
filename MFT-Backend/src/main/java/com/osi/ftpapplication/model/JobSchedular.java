package com.osi.ftpapplication.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy=false)
public class JobSchedular {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	Integer id;
	String schedulerName;
	LocalDateTime  datetime;
	String frequency;
	Integer sourceLocationID;
 	Integer destinationLocationID;
 	
 	
	public JobSchedular() {
	
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getSchedulerName() {
		return schedulerName;
	}


	public void setSchedulerName(String schedulerName) {
		this.schedulerName = schedulerName;
	}


	public LocalDateTime getDatetime() {
		return datetime;
	}


	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}


	public String getFrequency() {
		return frequency;
	}


	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}


	public Integer getSourceLocationID() {
		return sourceLocationID;
	}


	public void setSourceLocationID(Integer sourceLocationID) {
		this.sourceLocationID = sourceLocationID;
	}


	public Integer getDestinationLocationID() {
		return destinationLocationID;
	}


	public void setDestinationLocationID(Integer destinationLocationID) {
		this.destinationLocationID = destinationLocationID;
	}


	@Override
	public String toString() {
		return "JobSchedular [id=" + id + ", schedulerName=" + schedulerName + ", datetime=" + datetime + ", frequency="
				+ frequency + ", sourceLocationID=" + sourceLocationID + ", destinationLocationID="
				+ destinationLocationID + "]";
	}
	
 	
 	
}
