package org.eclipse.om2m.ipe.sample.model;

import java.time.LocalDate;
import java.time.Period;

import com.google.gson.JsonElement;

public class User {
	private String id;
	private String name;
	private String email;
	private String password;
	private String dayOfBirth;
	private String weight;
	private String age;
	private String height;
	private String gender;
	private String location;
	private String isCaretakers;
	private String smokingStatus;
	
	public static void main(String[] args) throws Exception {
		 String date = "22-02-1995";
		 String strarray[] = date.split("-") ;
		 date = "";
		 date = date + strarray[2] + "-" + strarray[1] + "-" + strarray[0];

	     System.out.println(date);

		 
			//default, ISO_LOCAL_DATE
	        LocalDate localDate = LocalDate.parse(date);
	        int a = calculateAge(localDate);
	        System.out.println(a);
	}
	
	public User(String id, String name, String email, String password, String dayOfBirth, 
			String weight, String height, 
			String gender, String location, String isCaretakers, String smokingStatus){
    	this.id = id;
    	this.name = name;
    	this.email = email;
    	this.password = password;
    	this.dayOfBirth = dayOfBirth;
    	this.weight = weight;
    	this.height = height;
    	this.gender = gender;
    	this.location = location;
    	this.isCaretakers = isCaretakers;
    	this.smokingStatus = smokingStatus;
		String strarray[] = dayOfBirth.split("-") ;
		String date = "";
		date = date + strarray[2] + "-" + strarray[1] + "-" + strarray[0];
		LocalDate localDate = LocalDate.parse(date);
        int a = calculateAge(localDate);
		this.age = String.valueOf(a);

	}
	
	public User(JsonElement params) {
		name = params.getAsJsonObject().get("name").getAsString();;
		email = params.getAsJsonObject().get("email").getAsString();
		password = params.getAsJsonObject().get("password").getAsString();
		dayOfBirth = params.getAsJsonObject().get("day_of_birth").getAsString();
		weight = params.getAsJsonObject().get("weight").getAsString();
		height = params.getAsJsonObject().get("height").getAsString();
		gender = params.getAsJsonObject().get("gender").getAsString();
		location = params.getAsJsonObject().get("location").getAsString();
		smokingStatus = params.getAsJsonObject().get("smoking_status").getAsString();
		isCaretakers = params.getAsJsonObject().get("is_caretakers").getAsString();			
		String strarray[] = dayOfBirth.split("-") ;
		String date = "";
		date = date + strarray[2] + "-" + strarray[1] + "-" + strarray[0];
		LocalDate localDate = LocalDate.parse(date);
        int a = calculateAge(localDate);
		this.age = String.valueOf(a);
	}
	
	public static int calculateAge(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        if (birthDate != null) {
            return Period.between(birthDate, today).getYears();
        } else {
            return 0;
        }
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDayOfBirth() {
		return dayOfBirth;
	}

	public void setDayOfBirth(String dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getIsCaretakers() {
		return isCaretakers;
	}

	public void setIsCaretakers(String isCaretakers) {
		this.isCaretakers = isCaretakers;
	}

	public String getSmokingStatus() {
		return smokingStatus;
	}

	public void setSmokingStatus(String smokingStatus) {
		this.smokingStatus = smokingStatus;
	}
}
