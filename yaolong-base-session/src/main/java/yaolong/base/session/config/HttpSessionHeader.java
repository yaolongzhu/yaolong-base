package yaolong.base.session.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.session.Session;
import org.springframework.util.Assert;

import yaolong.base.session.cons.Constant;

public class HttpSessionHeader implements SessionConfiguration {

    public String getRequestedSessionId(HttpServletRequest request) {
        return request.getHeader(Constant.headerName);
    }

    public void onNewSession(Session session, HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(Constant.headerName, session.getId());
    }

    public void onInvalidateSession(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader(Constant.headerName, "");
    }

    public void setSessionKey(String sessionKey) {
        Assert.notNull(Constant.headerName, "headerName cannot be null");
        Constant.headerName = sessionKey;
    }

}
