// UserMgmtServiceImpl.java 

package com.sk.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.sk.bindings.ActivateUser;
import com.sk.bindings.LoginCredentials;
import com.sk.bindings.RecoverPassword;
import com.sk.bindings.UserAccount;
import com.sk.entity.UserMaster;
import com.sk.repository.IUserMasterRepository;
import com.sk.utils.EmailUtils;

@Service
public class UserMgmtServiceImpl implements IUserMgmtService 
{
	@Autowired
	private IUserMasterRepository userMasterRepo;
	@Autowired
	private EmailUtils emailUtils;
	@Autowired
	private Environment env;
	

	@Override
	public String registerUser(UserAccount user) throws Exception {

	    // Convert UserAccount object data to UserMaster object (Entity object) data
	    UserMaster master = new UserMaster();
	    BeanUtils.copyProperties(user, master);

	    // Set random string of 6 chars as password
	    String tempPwd = generateRandomPassword(6);
	    master.setPassword(tempPwd);

	    master.setActive_sw("InActive");

	    // Save object
	    UserMaster savedMaster = userMasterRepo.save(master);

	    // Perform send the mail Operation
	    String subject = "User Registration Success";
	    String body = readEmailMessageBody(env.getProperty("mailbody.registeruser.location"), user.getName(), tempPwd);
	    emailUtils.sendEmailMessage(user.getEmail(), subject, body);

	    // Return message
	    return savedMaster != null ? "User is registered with Id value::" + savedMaster.getUserId()+" Check mail for temporary password ": "Problem in user registration";
	}// end of registerUser()
	
	
	

/*	@Override
 	public String activateUserAccount(ActivateUser user) 
	{
		
	    // Convert ActivateUser obj to Entity obj (UserMaster obj) data
	    UserMaster master = new UserMaster();
	    master.setEmail(user.getEmail());
	    master.setPassword(user.getTempPassword());

	    // check the record available by using email and temp password
	    Example<UserMaster> example = Example.of(master);
	    // select * from JRTP_USER_MASTER WHERE mail=? and password=?
	    List<UserMaster> list = userMasterRepo.findAll(example);
	    
	    if(list.size()!=0) {
	        //get Entity object
	        UserMaster entity = list.get(0);

	        //set the password
	        entity.setPassword(user.getConfirmPassword());

	        // change the user account status to active
	        entity.setActive_sw("Active");

	        //update the obj
	        UserMaster updatedEntity = userMasterRepo.save(entity);

	        return "User is activated with new Password"; 
	    }
 
		return " User is not found for activation";
	}  */
	
	          // OR
	
	@Override
	public String activateUserAccount(ActivateUser user) {

	    // Use findBy method
	    UserMaster entity = userMasterRepo.findByEmailAndPassword(user.getEmail(), user.getTempPassword());

	    if (entity == null) {
	        return "User is not found for activation";
	    } else {
	        // Set the password
	        entity.setPassword(user.getConfirmPassword());

	        // Change the user account status to active
	        entity.setActive_sw("Active");

	        // Update the obj
	        UserMaster updatedEntity = userMasterRepo.save(entity);

	        return "User is activated with new Password";
	    }
	} // end of activateUserAccount()
	
	
	

	@Override
	public List<UserAccount> listUsers() {
	    // Load all entities
	    List<UserMaster> listEntities = userMasterRepo.findAll();

	    // Convert all entities to UserAccount objs
	    List<UserAccount> listUsers = new ArrayList<>();

	    listEntities.forEach(entity -> {
	        UserAccount user = new UserAccount();
	        BeanUtils.copyProperties(entity, user);
	        listUsers.add(user);
	    });

	    return listUsers;
	}  // end of listUsers()
	
	

	@Override
	public String login(LoginCredentials credentials) {

	    // Convert LoginCredentials obj to UserMaster obj (Entity obj)
	    UserMaster master = new UserMaster();
	    BeanUtils.copyProperties(credentials, master);

	    // Prepare Example obj
	    Example<UserMaster> example = Example.of(master);
	    List<UserMaster> listEntities = userMasterRepo.findAll(example);

	    if (listEntities.size() == 0) {
	        return "Invalid Credentials";
	    } else {
	        // Get entity obj
	        UserMaster entity = listEntities.get(0);

	        if (entity.getActive_sw().equalsIgnoreCase("Active")) {
	            return "Valid credentials and Login successful";
	        } else {
	            return "User Account is not active";
	        }
	    }
	} // end of login()
	
	
	@Override
	public UserAccount showUserByUserId(Integer id) {

	    // Load the user by user id
	    Optional<UserMaster> opt = userMasterRepo.findById(id);
	    UserAccount account = null;
	    if (opt.isPresent()) {
	         account = new UserAccount();
	        BeanUtils.copyProperties(opt.get(), account);
	    }

	    return account;
	} // end of showUserByUserId()
	
	

	@Override
	public UserAccount showUserByEmailAndName(String email, String name) {

	    // Use the custom findBy(-) method
	    UserMaster master = userMasterRepo.findByNameAndEmail(name, email);

	    UserAccount account = null;

	    if (master != null) {
	        account = new UserAccount();
	        BeanUtils.copyProperties(master, account);
	    }

	    return account;
	} // end of showUserByEmailAndName()
	
	
	
	
	@Override
	public String updateUser(UserAccount user) {

	    // Use the custom findBy(-) method
	    Optional<UserMaster> opt = userMasterRepo.findById(user.getUserId());

	    if (opt.isPresent()) {
	        // Get Entity object
	        UserMaster master = opt.get();

	        BeanUtils.copyProperties(user, master);
	        userMasterRepo.save(master);

	        return "User Details are updated";
	    } else {
	        return "User not found for updation";
	    }
	} // end of updateUser()
	
	
	
	@Override
	public String deleteUserById(Integer id) {

	    // Load the obj
	    Optional<UserMaster> opt = userMasterRepo.findById(id);

	    if (opt.isPresent()) {
	        userMasterRepo.deleteById(id);
	        return "User is deleted";
	    }

	    return "User is not found for deletion";
	} // end of deleteUserById()
	
	

	@Override
	public String changeUserStatus(Integer id, String status) {

	    // Load the obj
	    Optional<UserMaster> opt = userMasterRepo.findById(id);

	    if (opt.isPresent()) {
	        // Get Entity obj
	        UserMaster master = opt.get();

	        // Change the status
	        master.setActive_sw(status);

	        // Update the obj 
	        userMasterRepo.save(master);

	        return "User status changed";
	    }

	    return "user not found for changing the status";
	    
	} // end of changeUserStatus()
	
	
	
	

	@Override
	public String recoverPassword(RecoverPassword recover) throws Exception {

	    // Get UserMaster (Entity obj) by name, email
	    UserMaster master = userMasterRepo.findByNameAndEmail(recover.getName(), recover.getEmail());

	    if (master != null) {
	        String pwd = master.getPassword();

	        // Send the recovered password to the email account
	        String subject = "mail for password recovery";
	        String mailBody = readEmailMessageBody(env.getProperty("mailbody.recoverpwd.location"), recover.getName(), pwd);
	        emailUtils.sendEmailMessage(recover.getEmail(), subject, mailBody);

	        return recover.getName() + "  mail is sent having recover password";
	    }

	    return "User and email is not found";
	} // end of recoverPassword()
	
	
	private String readEmailMessageBody(String fileName, String fullName , String pwd) throws Exception
	{
		String mailBody = null;
		String url = "http://localhost:4041/user-api/activate";

	    try (FileReader reader = new FileReader(fileName);
	         BufferedReader br = new BufferedReader(reader)) 
	        {

	        // Read file content to StringBuffer object line by line
	        StringBuffer buffer = new StringBuffer();
	        String line = null;

	        do {
	            line = br.readLine();
	            if (line != null) {
	                buffer.append(line);
	            }
	        } while (line != null);

	        mailBody = buffer.toString();
	        mailBody = mailBody.replace("{FULL-NAME}", fullName);
	        mailBody = mailBody.replace("{PWD}", pwd);
	        mailBody = mailBody.replace("{URL}", url);

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }

	    return mailBody;
		
	}
	
	
	// helper method for this class
	private String generateRandomPassword(int length) {

	    // a list of characters to choose from in form of a string
	    String AlphaNumericStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	    // creating a StringBuilder size of AlphaNumericStr
	    StringBuilder randomWord = new StringBuilder(length);

	    for (int i = 0; i < length; i++) {

	        // generating a random number using math.random() (gives pseudo random number 0.0 to 1.0)
	        int ch = (int) (AlphaNumericStr.length() * Math.random());

	        // adding Random character one by one at the end of randonword
	        randomWord.append(AlphaNumericStr.charAt(ch));
	    }

	    return randomWord.toString();
	    
	} // end of generateRandomPassword()
	

	
	

} // end of class 
