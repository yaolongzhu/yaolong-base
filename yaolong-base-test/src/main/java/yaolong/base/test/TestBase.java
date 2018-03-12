package yaolong.base.test;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 */
// @SuppressWarnings({ "deprecation", "unused" })
@ContextConfiguration(locations = {
    "classpath*:META-INF/spring/*.xml"
})
// @TransactionConfiguration(transactionManager =
// "base_dataSourceTransactionManager", defaultRollback = true)
@Transactional("base_dataSourceTransactionManager")
// @RunWith(SpringJUnit4ClassRunner.class)
@Rollback
public class TestBase extends AbstractTransactionalJUnit4SpringContextTests {
    //	@SuppressWarnings("unused")
    //	private static final Log log = LogFactory.getLog(TestBase.class);
}
