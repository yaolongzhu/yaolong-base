package yaolong.base.common.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 *
 * @author
 *
 */
public class BaseSerializer implements ObjectSerializer, ValueFilter {

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		SerializeWriter out = serializer.getWriter();
		if (object == null) {
			if (out.isEnabled(SerializerFeature.WriteNullNumberAsZero)) {
				out.write('0');
			} else {
				out.writeNull();
			}
			return;
		}
		out.writeString(object.toString());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object process(Object object, String name, Object value) {
		if (null != value) {
			if (value instanceof java.lang.Long) {
				return value.toString();
			} else if (value instanceof java.util.List) {
				List nList = (List) value;
				processList(nList);
				return nList;
			} else if (value instanceof Long[]) {
				Long[] array = (Long[]) value;
				return processLong(array);
			} else if (value instanceof long[]) {
				long[] array = (long[]) value;
				return processLong(array);
			}
			// else if (value instanceof java.util.Map) {
			// Map nMap = (Map) value;
			// processMap(nMap);
			// return nMap;
			// }
		}
		return value;
	}

	// @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Object> processList(List<Object> list) {
		if (list != null) {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				Object obj = list.get(i);
				if (obj instanceof java.lang.Long) {
					list.set(i, obj.toString());
					// } else if (obj instanceof java.util.List) {
					// List nList = (List) obj;
					// List rList = processList(nList);
					// list.set(i, rList);
					// } else if (obj instanceof java.util.Map) {
					// Map map = (Map) obj;
					// Map rMap = processMap(map);
					// list.set(i, rMap);
				}
			}
		}
		return list;
	}

	public String[] processLong(Long[] array) {
		if (array != null) {
			int size = array.length;
			String[] strs = new String[size];
			for (int i = 0; i < size; i++) {
				Long temp = array[i];
				if (temp != null) {
					strs[i] = array[i].toString();
				}
			}
			return strs;
		}
		return null;
	}

	public String[] processLong(long[] array) {
		if (array != null) {
			int size = array.length;
			String[] strs = new String[size];
			for (int i = 0; i < size; i++) {
				Long temp = array[i];
				if (temp != null) {
					strs[i] = array[i] + "";
				}
			}
			return strs;
		}
		return null;
	}

	// @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Object> processMap(Map<String, Object> map) {
		for (Entry<String, Object> entry : map.entrySet()) {
			Object obj = entry.getValue();
			String key = entry.getKey();
			if (obj instanceof java.lang.Long) {
				map.put(key, obj.toString());
				// } else if (obj instanceof java.util.List) {
				// List nList = (List) obj;
				// map.put(key, processList(nList));
				// } else if (obj instanceof java.util.Map) {
				// Map nMap = (Map) obj;
				// map.put(key, processMap(nMap));
			}
		}
		return map;
	}

}
