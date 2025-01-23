// IUserMgmtService.java

package com.sk.service;

import java.util.List;

import com.sk.bindings.ActivateUser;
import com.sk.bindings.LoginCredentials;
import com.sk.bindings.RecoverPassword;
import com.sk.bindings.UserAccount;

public interface IUserMgmtService {

    public String registerUser(UserAccount user) throws Exception;

    public String activateUserAccount(ActivateUser user);

    public String login(LoginCredentials credentials);

    public List<UserAccount> listUsers();

    public UserAccount showUserByUserId(Integer id);

    public UserAccount showUserByEmailAndName(String email, String name);

    public String updateUser(UserAccount user);

    public String deleteUserById(Integer id);

    public String changeUserStatus(Integer id, String status);

    public String recoverPassword(RecoverPassword recover) throws Exception;

}
