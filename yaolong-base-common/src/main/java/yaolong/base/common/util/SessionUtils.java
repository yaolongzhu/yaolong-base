package yaolong.base.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import yaolong.base.common.ReturnCode;
import yaolong.base.common.exception.BaseException;
import yaolong.base.inf.Audit;
import yaolong.base.inf.UserInfo;

/**
 *
 * @author
 *
 */
public class SessionUtils {

	public static final String MSF_SESSION_USER = "hdp_session_user";

	public static final String MSF_SESSION_AUDIT = "hdp_session_audit";

	/**
	 * 
	 * @return
	 */
	public static UserInfo getUser() {
	    Object obj = getHttpSession().getAttribute(MSF_SESSION_USER);
        if (obj == null) {
            throw new BaseException("Not Logged In.", ReturnCode.HDP_UN_LOGIN);
        }
		return (UserInfo) obj;
	}
	
	/**
	 * 
	 * @return
	 */
	public static Audit getAudit() {
	    Object obj = getHttpSession().getAttribute(MSF_SESSION_AUDIT);
	    if (obj == null) {
            throw new BaseException("Audit Error", ReturnCode.HDP_UN_LOGIN);
        }
		return (Audit) obj;
	}

	/**
	 * 
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes instanceof ServletRequestAttributes) {
			ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
			return sra.getRequest();
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public static HttpSession getHttpSession() {
		HttpServletRequest request = getHttpServletRequest();
		if (request != null) {
			return request.getSession();
		}
		return null;
	}


}
