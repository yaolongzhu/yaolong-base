
package yaolong.base.test;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import yaolong.base.inf.Audit;
import yaolong.base.inf.User;


public abstract class AbstractTest extends TestBase {

    private User user;

    private Audit audit;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    MockHttpSession session;

    MockHttpServletRequest request;

    public void startSession() {
        session = new MockHttpSession();
        user = new User(-100L, 1L, "1.0.0", "admintest", "开发测试");
        audit = new Audit(-100L, 1L, "admintest");
        session.setAttribute("hdp_session_user", user);
        session.setAttribute("hdp_session_audit", audit);
    }

    public void startSession(Long userId, Long tenantId) {
        session = new MockHttpSession();
        user = new User(userId, tenantId, "1.0.0", "admintest", "开发测试");
        audit = new Audit(userId, tenantId, "admintest");
        session.setAttribute("hdp_session_user", user);
        session.setAttribute("hdp_session_audit", audit);
    }

    protected void endSession() {
        session.clearAttributes();
        session = null;
    }

    protected void startRequest() {
        request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    protected void endRequest() {
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).requestCompleted();
        RequestContextHolder.resetRequestAttributes();
        request = null;
    }

    @Before
    public void setHdp() {
        startSession();
        startRequest();
    }

    @After
    public void endTest() {
        endRequest();
        endSession();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

}
