package yaolong.base.ar.kit.json;

/**
 *
 * @author
 *
 */
public class JJsonFactory implements IFactory {

	private static final JJsonFactory me = new JJsonFactory();

	public static JJsonFactory me() {
		return me;
	}

	public Json getJson() {
		return new JJson();
	}
}
