package yaolong.base.pst.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import yaolong.base.ar.Record;

/**
 *
 * @author Luo Guoxiong
 *
 */
public class RecordUtils {

	/**
	 * 把Record转为Map，方便Controller层做Json转换
	 * 
	 * @param model
	 * @return
	 */
	public static Map<String, Object> convertRecord2Map(Record record) {
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
	public static List<Map<String, Object>> convertRecordList2MapList(List<Record> recordList) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (recordList.size() > 0) {
			for (Record Record : recordList) {
				list.add(convertRecord2Map(Record));
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

}
