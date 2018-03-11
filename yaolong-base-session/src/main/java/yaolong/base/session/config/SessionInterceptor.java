package yaolong.base.session.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

import yaolong.base.session.bean.Result;
import yaolong.base.session.cons.Constant;

/**
 * @author
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 跨域访问CORS
        response.addHeader("Access-Control-Allow-Origin", Constant.origin);
        response.addHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,PUT,DELETE,HEAD");
        response.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type,tid," + Constant.headerName);
        response.addHeader("Access-Control-Expose-Headers", "x-requested-with,content-type,tid," + Constant.headerName);
        response.addHeader("Access-Control-Max-Age", Constant.maxAge);
        response.addHeader("Access-Control-Allow-Credentials", "true");
        // 让请求，不被缓存
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        if (!repeatValidate(request, response, handler)) {
            return false;
        }
        return super.preHandle(request, response, handler);
    }

    public boolean repeatValidate(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        HttpSession session = request.getSession();
        Long crr = getHeaderToken(request);
        if (crr == 0L) {
            return true;
        }
        String path = request.getServletPath();
        Object obj = session.getAttribute(path);
        if (obj != null) {
            Long last = (Long) obj;
            if (crr > last) {
                //合法请求
                session.setAttribute(path, crr);
            } else {
                //重复请求，写出错误信息
                Result result = new Result();
                result.putObj("crr", crr);
                result.putObj("last", last);
                result.putObj("path", path);
                writeErrResult(response, result);
                return false;
            }
        } else {
            session.setAttribute(path, crr);
        }
        return true;
    }

    public Long getHeaderToken(HttpServletRequest request) {
        String headerToken = request.getHeader(Constant.tokenName);
        if (headerToken != null) {
            return Long.parseLong(headerToken);
        }
        return 0L;
    }

    public void writeErrResult(HttpServletResponse response, Result result) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
        writer.flush();
    }

}
