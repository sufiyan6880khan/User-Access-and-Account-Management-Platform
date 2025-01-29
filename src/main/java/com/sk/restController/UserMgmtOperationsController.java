
// UserMgmtOperationsController.java 

package com.sk.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sk.bindings.ActivateUser;
import com.sk.bindings.LoginCredentials;
import com.sk.bindings.RecoverPassword;
import com.sk.bindings.UserAccount;
import com.sk.service.IUserMgmtService;

@RestController
@RequestMapping("/user-api")
public class UserMgmtOperationsController 
{
	
	@Autowired
	private IUserMgmtService userService;
	

	    @PostMapping("/save")
	    public ResponseEntity<String> saveUser(@RequestBody UserAccount account) {
	        try {
	            String resultMsg = userService.registerUser(account);
	            System.out.println("Hello");
	            return new ResponseEntity<>(resultMsg, HttpStatus.CREATED);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } // end of saveUser()
	    
	    

	    @PostMapping("/activate")
	    public ResponseEntity<String> activateUser(@RequestBody ActivateUser user) {
	        try {
	            String resultMsg = userService.activateUserAccount(user);
	            return new ResponseEntity<>(resultMsg, HttpStatus.CREATED);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } // end of activateUser()
	    
	    
	    
	    @PostMapping("/login")
	    public ResponseEntity<String> performLogin(@RequestBody LoginCredentials credentials) {
	        try {
	            String resultMsg = userService.login(credentials);
	            return new ResponseEntity<>(resultMsg, HttpStatus.OK);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }  // end of performLogin()
	    
	    

	    @GetMapping("/report")
	    public ResponseEntity<?> showUsers() {
	        try {
	            List<UserAccount> userList = userService.listUsers();
	            return new ResponseEntity<>(userList, HttpStatus.OK);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } // end of showUsers()

	    
	    
	    @GetMapping("/find/{id}")
	    public ResponseEntity<?> showUserByUserId(@PathVariable Integer id) {
	        try {
	            UserAccount account = userService.showUserByUserId(id);
	            return new ResponseEntity<>(account, HttpStatus.OK);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } // end of showUserByUserId()
	    
	    

	    @GetMapping("/find/{email}/{name}")
	    public ResponseEntity<?> showUserByEmailAndName(@PathVariable String email, @PathVariable String name) {
	        try {
	            UserAccount account = userService.showUserByEmailAndName(email, name);
	            return new ResponseEntity<>(account, HttpStatus.OK);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } // end of showUserByEmailAndName()
	    
	    
	    
	    @PutMapping("/update")
	    public ResponseEntity<String> updateUserDetails(@RequestBody UserAccount account) {
	        try {
	            String resultMsg = userService.updateUser(account);
	            return new ResponseEntity<>(resultMsg, HttpStatus.OK);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } // end of updateUserDetails()
	    
	    

	    @DeleteMapping("/delete/{id}")
	    public ResponseEntity<String> deleteUserById(@PathVariable Integer id) {
	        try {
	            String resultMsg = userService.deleteUserById(id);
	            return new ResponseEntity<>(resultMsg, HttpStatus.OK);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } // end of updateUserDetails()
	    

	    
	    @PatchMapping("/changeStatus/{id}/{status}")
	    public ResponseEntity<String> changeStatus(@PathVariable Integer id, @PathVariable String status) {
	        try {
	            String resultMsg = userService.changeUserStatus(id, status);
	            return new ResponseEntity<>(resultMsg, HttpStatus.OK);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } // end of changeStatus()

	    
	    
	    @PostMapping("/recoverPassword")
	    public ResponseEntity<String> recoverPassword(@RequestBody RecoverPassword recover) {
	        try {
	            String resultMsg = userService.recoverPassword(recover);
	            return new ResponseEntity<>(resultMsg, HttpStatus.OK);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } // end of recoverPassword



} // end of class























