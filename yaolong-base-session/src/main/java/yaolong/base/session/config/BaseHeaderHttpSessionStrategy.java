package yaolong.base.session.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.session.Session;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;

/**
 *
 * @author
 *
 */
public class BaseHeaderHttpSessionStrategy extends HeaderHttpSessionStrategy {

	private SessionConfiguration strategy;

	public BaseHeaderHttpSessionStrategy() {
		super();
	}

	public BaseHeaderHttpSessionStrategy(SessionConfiguration strategy) {
		super();
		this.strategy = strategy;
	}

	@Override
	public String getRequestedSessionId(HttpServletRequest request) {
		return strategy.getRequestedSessionId(request);
	}

	@Override
	public void onNewSession(Session session, HttpServletRequest request, HttpServletResponse response) {
		strategy.onNewSession(session, request, response);
	}

	@Override
	public void onInvalidateSession(HttpServletRequest request, HttpServletResponse response) {
		strategy.onInvalidateSession(request, response);
	}

	@Override
	public void setHeaderName(String headerName) {
		strategy.setSessionKey(headerName);
	}

	public SessionConfiguration getStrategy() {
		return strategy;
	}

	public void setStrategy(SessionConfiguration strategy) {
		this.strategy = strategy;
	}

}
