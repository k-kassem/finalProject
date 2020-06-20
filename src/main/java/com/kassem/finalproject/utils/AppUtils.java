package com.kassem.finalproject.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.kassem.finalproject.service.UserService;

public class AppUtils {

	@Autowired
	UserService userService;
	public static boolean validateEmail(String email) {
		String emailPattern = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
		
	}
	
	public static List<String> getStringFromEnum(Enum<?>[] roles){
    	List<String> roleStr = new ArrayList<>();
    	for(Enum<?> role : roles) {
    		roleStr.add(role.toString());
    	}
    	return roleStr;
    }
	public static String generateId() {
		// replace '-' by '' to work properly with DB 
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
	
	public static boolean validDate(LocalDate now , LocalDate date2) {
		if(now.compareTo(date2)>0)
			return false;
		return true;
	}
	public  boolean isUserExist(String userName) {
		if(userService.getUserByUsername(userName) != null)
			return true;
		return false;
	}
}
