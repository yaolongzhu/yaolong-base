package yaolong.base.pst.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import yaolong.base.ar.Model;
import yaolong.base.ar.Record;
import yaolong.base.ar.Table;
import yaolong.base.ar.TableMapping;

/**
 * 
 * @author LUOGX
 *
 * @param <M>
 */
public class MapUtils<M extends Model<?>> {

	/**
	 * 把Map中的值复制到Model中，忽略数据库不存在的字段
	 * 
	 * @param model
	 *            Model实体
	 * @param map
	 *            Map实体
	 */
	@SuppressWarnings("rawtypes")
	public void copyMap2Model(M model, Map<String, Object> map) {
		Table table = TableMapping.me().getTable(model.getClass());
		if ((model == null) || (map == null)) {
			return;
		}
		Iterator names = map.keySet().iterator();
		while (names.hasNext()) {
			String name = (String) names.next();
			if (name == null) {
				continue;
			}
			Object value = map.get(name);
			if (table.hasColumnLabel(name)) {
				if (value != null) {
					Class<?> type = table.getColumnType(name);
					if (type == Long.class || type == long.class) {
						model.set(name, Long.parseLong(value.toString()));
					} else {
						model.set(name, value);
					}
				}
			}
		}
	}

	/**
	 * 把Model转为Map，方便Controller层做Json转换
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> convertModel2Map(M model) {
		if (model == null) {
			return null;
		}
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> map = yaolong.base.ar.CPI.getAttrs((Model) model);
		for (Entry<String, Object> entry : map.entrySet()) {
			Object obj = entry.getValue();
			if (obj instanceof Long) {
				res.put(entry.getKey(), obj.toString());
			}else{
				res.put(entry.getKey(), obj);
			}
		}
		return res;
	}

	/**
	 * 把Model集合转换为Map集合，方便Controller层做Json转换
	 * 
	 * @param model
	 * @return
	 */
	public List<Map<String, Object>> convertModelList2MapList(List<M> modelList) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (modelList.size() > 0) {
			for (M m : modelList) {
				list.add(this.convertModel2Map(m));
			}
		}
		return list;
	}

	/**
	 * 把Record转为Map，方便Controller层做Json转换
	 * 
	 * @param model
	 * @return
	 */
	public Map<String, Object> convertRecord2Map(Record record) {
		if (record == null) {
			return null;
		}
		Map<String, Object> res = new HashMap<String, Object>();
		Map<String, Object> columnMap = record.getColumns();
		for (Entry<String, Object> entry : columnMap.entrySet()) {
			Object obj = entry.getValue();
			if (obj instanceof Long) {
				res.put(entry.getKey(), obj.toString());
			}else{
				res.put(entry.getKey(), obj);
			}
		}
		return res;
	}

	/**
	 * 把Record集合转换为Map集合，方便Controller层做Json转换
	 * 
	 * @param model
	 * @return
	 */
	public List<Map<String, Object>> convertRecordList2MapList(List<Record> recordList) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (recordList.size() > 0) {
			for (Record Record : recordList) {
				list.add(this.convertRecord2Map(Record));
			}
		}
		return list;
	}

	public static boolean isDigit(String str) {
		if (null == str || str.length() == 0) {
			return false;
		}
		for (int i = str.length(); --i >= 0;) {
			int c = str.charAt(i);
			if (c < 48 || c > 57) {
				return false;
			}
		}
		return true;
	}

	// public void copyMaps2Models(List<M> modelList,
	// List<Map<String,Object>> maps) {
	// for(Map<String,Object>m :maps){
	// M mx=new M();
	// this..copyMap2Model(, m);
	// }
	// }

}
