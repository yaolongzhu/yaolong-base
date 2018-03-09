package yaolong.base.ar.log;

/**
 *
 * @author
 *
 */
public class JdkLogFactory implements ILogFactory {

	public Log getLog(Class<?> clazz) {
		return new JdkLog(clazz);
	}

	public Log getLog(String name) {
		return new JdkLog(name);
	}
}
