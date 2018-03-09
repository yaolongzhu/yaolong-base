package yaolong.base.ar;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class OrderedFieldContainerFactory implements IContainerFactory {

	public Map<String, Object> getAttrsMap() {
		return new LinkedHashMap();
	}

	public Map<String, Object> getColumnsMap() {
		return new LinkedHashMap();
	}

	public Set<String> getModifyFlagSet() {
		return new HashSet();
	}
}
