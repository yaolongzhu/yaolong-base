package yaolong.base.ar;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import yaolong.base.ar.log.Log;

/**
*
* @author
*
*/
public class SqlReporter implements InvocationHandler {
	
	private Connection conn;
	private static boolean logOn = false;
	private static final Log log = Log.getLog(SqlReporter.class);
	
	SqlReporter(Connection conn) {
		this.conn = conn;
	}
	
	public static void setLog(boolean on) {
		SqlReporter.logOn = on;
	}
	
	@SuppressWarnings("rawtypes")
	Connection getConnection() {
		Class clazz = conn.getClass();
		return (Connection)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{Connection.class}, this);
	}
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		try {
			if (method.getName().equals("prepareStatement")) {
				String info = "Sql: " + args[0];
				if (logOn)
					log.info(info);
				else
					System.out.println(info);
			}
			return method.invoke(conn, args);
		} catch (InvocationTargetException e) {
			throw e.getTargetException();
		}
	}
}


