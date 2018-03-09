package yaolong.base.inf.extension;

import yaolong.base.inf.UserInfo;

/**
 *
 * @author
 *
 */
public interface UserInfoExtension extends UserInfo {
	/**
	 * 将对象保存到用户属性中
	 * 
	 * @param key
	 * @param value
	 */
	Object putExtension(String key, Object value);

	/**
	 * 将对象从用户属性中移除
	 * 
	 * @param key
	 */
	Object removeExtension(String key);
}
