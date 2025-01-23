// userAccount.java

package com.sk.bindings;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount 
{
	
    private Integer userId; // will not be filled during user registration
	private String name;
	private String email;
	private Long mobileNo;
	private String gender = "female";
	private LocalDate dob = LocalDate.now();
	private Long adharNo;
}
