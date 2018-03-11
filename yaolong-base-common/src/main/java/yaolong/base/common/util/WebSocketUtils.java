package yaolong.base.common.util;

import org.springframework.session.Session;

import yaolong.base.common.ReturnCode;
import yaolong.base.common.exception.BaseException;
import yaolong.base.inf.Audit;
import yaolong.base.inf.UserInfo;
import yaolong.base.session.util.RepositoryUtils;

/**
 * @author
 */
public class WebSocketUtils {

    /**
     * 获取session
     * 
     * @param id
     * @return
     */
    public static Session getSession(String id) {
        return RepositoryUtils.getSession(id);
    }

    /**
     * @return
     */
    public static UserInfo getUser(String id) {
        Object obj = RepositoryUtils.getAttribute(id, SessionUtils.MSF_SESSION_USER);
        if (obj == null) {
            throw new BaseException("Not Logged In.", ReturnCode.HDP_UN_LOGIN);
        }
        return (UserInfo) obj;
    }

    /**
     * @return
     */
    public static Audit getAudit(String id) {
        Object obj = RepositoryUtils.getAttribute(id, SessionUtils.MSF_SESSION_AUDIT);
        if (obj == null) {
            throw new BaseException("Audit Error", ReturnCode.HDP_UN_LOGIN);
        }
        return (Audit) obj;
    }

    public static void get(String id, String attributeName) {
        RepositoryUtils.getAttribute(id, attributeName);
    }

}
