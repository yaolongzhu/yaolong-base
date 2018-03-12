package yaolong.base.mvc.minimal;

import java.util.Map;

import yaolong.base.common.util.JsonUtils;

/**
 *
 * @author
 *
 */
public class ViewModel {

	private Map<String, Object> data;

	public ViewModel() {
	}

	public ViewModel(Map<String, Object> data) {
		this.data = data;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(String data) {
		this.data = JsonUtils.parseHashMap(data);
	}

	public Object get(String key) {
		return data.get(key);
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return data.get(key).toString();
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Integer getInt(String key) {
		return Integer.parseInt(getString(key));
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Long getLong(String key) {
		return Long.parseLong(getString(key));
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Boolean getBoolean(String key) {
		return Boolean.parseBoolean(getString(key));
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Double getDouble(String key) {
		return Double.valueOf(getString(key));
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Float getFloat(String key) {
		return Float.valueOf(getString(key));
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public char getchar(String key) {
		char[] strChar = getString(key).toCharArray();
		return strChar[0];
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public char[] getCharacter(String key) {
		return getString(key).toCharArray();
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Object set(String key, Object value) {
		return data.put(key, value);
	}

	/**
	 * 
	 * @param map
	 */
	public void setMap(Map<String, Object> map) {
		this.data = map;
	}

}
