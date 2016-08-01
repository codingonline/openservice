package pop2016.openservice.service;

import java.util.List;

import pop2016.openservice.dao.UserDAO;
import pop2016.openservice.model.User;


public class UserManager {
	private  UserDAO userDao;
	public UserManager(){
		userDao = new UserDAO();
	}
	public User findUserByToken(String token){
		List<User> users = userDao.findByToken(token);
		if(users.size()>0)
			return users.get(0);
		else
			return null;
	}

}
