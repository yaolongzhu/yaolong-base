package yaolong.base.mvc.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * @author yaolong
 *
 */
public class SupportInterceptor extends HandlerInterceptorAdapter {
	private static final Log log = LogFactory.getLog(SupportInterceptor.class);

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		Object ctxPath = request.getAttribute("ctx");
		if (ctxPath == null) {
			request.setAttribute("ctx", request.getContextPath());
		} else {
			request.setAttribute("request-context-path", request.getContextPath());
			log.info("不建议手工设置默认上下文路径.");
		}
		super.postHandle(request, response, handler, modelAndView);
	}

}
