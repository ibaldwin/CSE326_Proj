package com.tutorconnect.entity;

import java.util.*;


public class Event {
private Calendar dateTime;	
private String creatorName;
private String location;
private String description;


public String getCreatorName() {
	return creatorName;
}
public Calendar getDateTime() {
	return dateTime;
}
public void setDateTime(Calendar dateTime) {
	this.dateTime = dateTime;
}
public void setCreatorName(String creatorName) {
	this.creatorName = creatorName;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

}
