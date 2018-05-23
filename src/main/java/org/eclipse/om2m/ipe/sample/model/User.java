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
			String weight, String age, String height, 
			String gender, String location, String isCaretakers, String smokingStatus){
    	this.id = id;
    	this.name = name;
    	this.email = email;
    	this.password = password;
    	this.dayOfBirth = dayOfBirth;
    	this.weight = weight;
    	this.age = age;
    	this.height = height;
    	this.gender = gender;
    	this.location = location;
    	this.isCaretakers = isCaretakers;
    	this.smokingStatus = smokingStatus;
	}
	
	public User(JsonElement params) {
		name = params.getAsJsonObject().get("name").getAsString();;
		age = params.getAsJsonObject().get("age").getAsString();
		String email = params.getAsJsonObject().get("email").getAsString();;
		String password = params.getAsJsonObject().get("password").getAsString();
		String dayOfBirth = params.getAsJsonObject().get("day_of_birth").getAsString();
		String weight = params.getAsJsonObject().get("weight").getAsString();
		String height = params.getAsJsonObject().get("height").getAsString();
		String gender = params.getAsJsonObject().get("gender").getAsString();
		String location = params.getAsJsonObject().get("location").getAsString();
		String smokingStatus = params.getAsJsonObject().get("smoking_status").getAsString();
	}
	
	public static int calculateAge(LocalDate birthDate) {
        LocalDate today = LocalDate.now();
        if (birthDate != null) {
            return Period.between(birthDate, today).getYears();
        } else {
            return 0;
        }
    }
}
