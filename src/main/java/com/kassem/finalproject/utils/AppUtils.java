package com.kassem.finalproject.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {

	
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
}
