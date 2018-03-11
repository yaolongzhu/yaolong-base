package yaolong.base.session.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.session.Session;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.DefaultCookieSerializer;

public class HttpSessionCookies implements SessionConfiguration {

	private CookieHttpSessionStrategy cookieStrategy = new CookieHttpSessionStrategy();

	public String getRequestedSessionId(HttpServletRequest request) {
		return cookieStrategy.getRequestedSessionId(request);
	}

	public void onNewSession(Session session, HttpServletRequest request, HttpServletResponse response) {
		cookieStrategy.onNewSession(session, request, response);

	}

	public void onInvalidateSession(HttpServletRequest request, HttpServletResponse response) {
		cookieStrategy.onInvalidateSession(request, response);
	}

	/**
	 * The name of the header to obtain the session id from. Default is
	 * "x-auth-token".
	 *
	 * @param headerName
	 *            the name of the header to obtain the session id from.
	 */
	public void setSessionKey(String sessionKey) {
		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
		serializer.setCookieName(sessionKey); // <1>
		serializer.setCookiePath("/"); // <2>
		serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");// <3>
		cookieStrategy.setCookieSerializer(serializer);
	}

}
