package com.osi.ftpapplication.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy=false)
public class JobHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	Integer  schedulerid;
	LocalDateTime datetime;
	String status;
	String description;
	
	public JobHistory() {
	
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSchedulerid() {
		return schedulerid;
	}
	public void setSchedulerid(Integer schedulerid) {
		this.schedulerid = schedulerid;
	}
	public LocalDateTime getDatetime() {
		return datetime;
	}
	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "JobHistory [id=" + id + ", schedulerid=" + schedulerid + ", datetime=" + datetime + ", status=" + status
				+ ", description=" + description + "]";
	}

	
	
	
	
}