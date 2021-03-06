package com.kassem.finalproject.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {



	@Id
	private long id;
	private String username;
	private String firstName;
	private String lastName;
	private String address;
	private String password;
	private String email;
	private String role;
	private Integer age;
	private String status;
	private String departement;
	private Integer salary;
	private LocalDate startDate;
	private String position;
	private Double nbOfLeaves;
	private Integer firstLogin;
	
	public User(String username,String firstName,String lastName,String address, String password,String email,String role ,Integer age,String status,
			String department,Integer salary,LocalDate startDate,String position,Double nbOfLeaves,Integer firstLogin) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.age = age;
		this.status = status;
		this.departement = department;
		this.salary = salary;
		this.startDate = startDate;
		this.position = position;
		this.nbOfLeaves = nbOfLeaves;
		this.firstLogin = firstLogin;
	}
	public User() {
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUserName(String userName) {
		this.username = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDepartement() {
		return departement;
	}
	public void setDepartement(String department) {
		this.departement = department;
	}
	public Integer getSalary() {
		return salary;
	}
	public void setSalary(Integer salary) {
		this.salary = salary;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
 
	public Double getNbOfLeaves() {
		return nbOfLeaves;
	}
	public void setNbOfLeaves(Double nbOfLeaves) {
		this.nbOfLeaves = nbOfLeaves;
	}

	public Integer getFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(Integer firstLogin) {
		this.firstLogin = firstLogin;
	}

	public enum RoleValues{
	    Admin {
	        public String toString() {
	            return "Admin";
	        }
	    },
	    User{
	    	public String ToString() {
	    		return "User";
	    	}
	    }
	}
	
	public enum Status{
		Single{
			public String toString() {
	            return "Single";
	        }
		},
		Married{
			public String toString() {
	            return "Married";
	        }
		}
	}
	
	public enum Departement{
		IT{
			public String toString() {
	            return "IT";
	        }
		},
		Developement{
			public String toString() {
	            return "Developement";
	        }
		},
		Management{
			public String toString() {
	            return "Management";
	        }
		},
		HR{
			public String toString() {
	            return "HR";
	        }
		},
		Finance{
			public String toString() {
	            return "Finance";
	        }
		}
	}
	
	public enum Position{
		IT_Employee{
			public String toString() {
	            return "IT Employee";
	        }
		},
		Developer{
			public String toString() {
	            return "Developer";
	        }
		},
		Telecom_Support{
			public String toString() {
	            return "Telecom Support";
	        }
		},
		Team_Leader{
			public String toString() {
	            return "Team Leader";
	        }
		},
		Manager{
			public String toString() {
	            return "Manager";
	        }
		}
	}
	

}
