package yaolong.base.ar.cache;

/**
 *
 * @author
 *
 */
public class EhCache implements ICache {

	@SuppressWarnings("unchecked")
	public <T> T get(String cacheName, Object key) {
		return (T) CacheKit.get(cacheName, key);
	}

	public void put(String cacheName, Object key, Object value) {
		CacheKit.put(cacheName, key, value);
	}

	public void remove(String cacheName, Object key) {
		CacheKit.remove(cacheName, key);
	}

	public void removeAll(String cacheName) {
		CacheKit.removeAll(cacheName);
	}
}