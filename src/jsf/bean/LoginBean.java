package jsf.bean;

import java.util.List;

import jsf.Global;
import jsf.dao.UserDAO;
import jsf.entity.User;

public class LoginBean {
	
	private UserDAO userDAO;
	
	private String msg;

	public String login() {
		String username = Global.getServletRequestMap().get("loginForm:username");
		String password = Global.getServletRequestMap().get("loginForm:password");
		
		List<User> userList = this.userDAO.findByName(username);
		for (User user : userList) {
			if(password.equals(user.getPassword())) {
				Global.getServletRequest().getSession().setAttribute("user", user);
				return "succeed";
			}
		}
		
		this.setMsg(username + ", Login FAILED!");
		return "fail";
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

}
