package yaolong.base.ar;

import yaolong.base.ar.interceptor.Interceptor;

/**
 *
 * @author
 *
 */
public class OneConnectionPerThread implements Interceptor {

//	public void intercept(Invocation inv) {
//		Connection conn = DbKit.config.getThreadLocalConnection();
//		if (conn != null) {
//			inv.invoke();
//			return;
//		}
//
//		try {
//			conn = DbKit.config.getConnection();
//			DbKit.config.setThreadLocalConnection(conn);
//			inv.invoke();
//		} catch (RuntimeException e) {
//			throw e;
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		} finally {
//			DbKit.config.removeThreadLocalConnection();
//			if (conn != null) {
//				try {
//					conn.close();
//				} catch (Exception e) {
//					LogKit.error(e.getMessage(), e);
//				}
//				;
//			}
//		}
//	}
}
