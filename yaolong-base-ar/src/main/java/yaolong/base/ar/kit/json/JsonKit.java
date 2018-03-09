package yaolong.base.ar.kit.json;

/**
 *
 * @author
 *
 */
public class JsonKit {

	public static String toJson(Object object) {
		return Json.getJson().toJson(object);
	}

	public static <T> T parse(String jsonString, Class<T> type) {
		return Json.getJson().parse(jsonString, type);
	}

	/**
	 * 兼容 jfinal 2.1 之前版本
	 */
	@Deprecated
	public static String toJson(Object value, int depth) {
		Json json = Json.getJson();
		// 仅 JFinalJson 实现支持 int depth 参数
		if (json instanceof JJson) {
			((JJson) json).setConvertDepth(depth);
		}
		return json.toJson(value);
	}

	/*
	 * public static String toJson(Object target, String dataPattern) { return
	 * null; }
	 */
}
