package jsf.bean;

import jsf.Global;

public class LogoutBean {

	private String msg;

	public String logout() {
		if (null == Global.getServletRequest().getSession().getAttribute("user")) {
			this.setMsg("Sorry, YOU ARE NOT Logined!");
			return "fail";
		}
		Global.getServletRequest().getSession().removeAttribute("user");
		this.setMsg("Logout Successful!");
		return "succeed";
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
