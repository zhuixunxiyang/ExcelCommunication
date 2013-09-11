package jsf;

import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Global {    
	private static ServletContext _servletContext;
	private static HttpServletResponse _servletReponse;
	private static HttpServletRequest _servletRequest;
	private static Map<String,String> _servletRequestMap;
	private static StringBuffer url;
	private HttpSession mySession;

	public HttpSession getMySession() {
		mySession = _servletRequest.getSession();
		return mySession;
	}

	public static HttpServletResponse getServletReponse() {
		_servletReponse = (HttpServletResponse)FacesContext.
		getCurrentInstance().getExternalContext().getResponse();
		return _servletReponse;
	}

	public static final ServletContext getServletContext() {
		//if(_servletContext==null)
			_servletContext = (ServletContext)FacesContext.
			getCurrentInstance().getExternalContext().getContext(); 
		return _servletContext; 
	}
	
	public static final HttpServletRequest getServletRequest() {
		//if(_servletRequest==null)
			_servletRequest = (HttpServletRequest)FacesContext.
			getCurrentInstance().getExternalContext().getRequest(); 
		return _servletRequest;
	}
	public static final Map<String,String> getServletRequestMap() {
		//if(_servletRequest==null)
			_servletRequestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap(); 
		return _servletRequestMap;
	}

	public static StringBuffer getUrl() {
		url = Global.getServletRequest().getRequestURL();
		return url;
	}

	public static void removeLoginSession(){
		HttpSession hs = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		if( hs != null){
			hs.removeAttribute("islogin");
			hs.removeAttribute("user");
		}
	}
	
}
