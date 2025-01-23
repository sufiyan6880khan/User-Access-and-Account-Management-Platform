// ActiveUser.java

package com.sk.bindings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivateUser 
{
	private String email;
	private String tempPassword;
	private String newPassword;
	private String confirmPassword;

}
