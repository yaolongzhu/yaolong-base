package yaolong.base.session.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.session.Session;

/**
*
* @author
*
*/
public interface SessionConfiguration {
	
	String getRequestedSessionId(HttpServletRequest request);

	void onNewSession(Session session, HttpServletRequest request, HttpServletResponse response);
	
	void onInvalidateSession(HttpServletRequest request, HttpServletResponse response);
	
	void setSessionKey(String sessionKey);
}


