package yaolong.base.session;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.DelegatingFilterProxy;

import yaolong.base.session.cons.Constant;

/**
 * @author
 */

public class SessionFilter extends DelegatingFilterProxy {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
            HttpServletResponse response = (HttpServletResponse) res;
            response.addHeader("Access-Control-Allow-Origin", Constant.origin);
            // CONNECT,TRACE
            response.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,PUT,DELETE,HEAD");
            response.addHeader("Access-Control-Max-Age", Constant.maxAge);
            response.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,tid," + Constant.headerName);
            response.addHeader("Access-Control-Expose-Headers", "x-requested-with,content-type,tid," + Constant.headerName);
            response.addHeader("Access-Control-Allow-Credentials", "true");
        } else {
            super.doFilter(req, res, filterChain);
        }
    }
}
