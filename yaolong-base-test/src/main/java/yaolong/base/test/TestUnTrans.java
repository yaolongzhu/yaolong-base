package yaolong.base.test;

import org.junit.Assert;
import org.junit.Test;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * 
 * @author
 *
 */
@SuppressWarnings("unused")
@ContextConfiguration(locations = { "classpath*:META-INF/spring/*.xml" })
public class TestUnTrans extends AbstractJUnit4SpringContextTests {
	private static final Log log = LogFactory.getLog(TestUnTrans.class);
}
