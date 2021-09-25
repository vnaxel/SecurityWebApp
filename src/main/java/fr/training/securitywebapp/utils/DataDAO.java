package fr.training.securitywebapp.utils;

import java.util.HashMap;
import java.util.Map;

import fr.training.securitywebapp.bean.UserAccount;
import fr.training.securitywebapp.config.SecurityConfig;

public class DataDAO {
	
	private static final Map<String, UserAccount> mapUsers = new HashMap<String, UserAccount>();
	
	static {
		initUsers();
	}
	
	private static void initUsers() {
		
		UserAccount emp = new UserAccount("employee1", "123", UserAccount.GENDER_MALE, SecurityConfig.ROLE_EMPLOYEE);
		
		UserAccount mng = new UserAccount("manager1", "123", UserAccount.GENDER_MALE, SecurityConfig.ROLE_EMPLOYEE, SecurityConfig.ROLE_MANAGER);
		
		mapUsers.put(emp.getUserName(), emp);
		mapUsers.put(mng.getUserName(), mng);
	}
	
	public static UserAccount findUser(String userName, String password) {
		UserAccount u = mapUsers.get(userName);
		if (u != null && u.getPassword().equals(password)) {
			return u;
		} else {
			return null;
		}
		
	}
	
}
