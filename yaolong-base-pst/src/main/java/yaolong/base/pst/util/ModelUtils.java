package yaolong.base.pst.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import yaolong.base.ar.Model;
import yaolong.base.ar.Table;
import yaolong.base.ar.TableMapping;
import yaolong.base.ar.kit.StrKit;
import yaolong.base.pst.annotation.Tab;

/**
 * @author Luo Guoxiong
 */
public class ModelUtils {

    /**
     * 把Model转为Map，方便Controller层做Json转换
     * 
     * @param model
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> convertModel2Map(Model model) {
        if (model == null) {
            return null;
        }
        Map<String, Object> res = new HashMap<String, Object>();
        Map<String, Object> map = yaolong.base.ar.CPI.getAttrs((Model) model);
        for (Entry<String, Object> entry: map.entrySet()) {
            Object obj = entry.getValue();
            if (obj instanceof Long) {
                res.put(entry.getKey(), obj.toString());
            } else {
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
    @SuppressWarnings("rawtypes")
    public static List<Map<String, Object>> convertModelList2MapList(List<? extends Model> modelList) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (modelList.size() > 0) {
            for (Model m: modelList) {
                list.add(convertModel2Map(m));
            }
        }
        return list;
    }

    /**
     * @param m
     * @return
     */
    @SuppressWarnings({
        "rawtypes", "unchecked"
    })
    private static Class<? extends Model> getUsefulClass(Model m) {
        Class c = m.getClass();
        return c.getName().indexOf("EnhancerByCGLIB") == -1 ? c : c.getSuperclass();
    }

    /**
     * @param m
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Table getTable(Model m) {
        return TableMapping.me().getTable(getUsefulClass(m));
    }

    /**
     * @param m
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getTableName(Model m) {
        Tab t = m.getClass().getAnnotation(Tab.class);
        return t.value();
    }

    /**
     * @param m
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getPrimaryKey(Model m) {
        Tab t = m.getClass().getAnnotation(Tab.class);
        String pkName = t.pk();
        if (StrKit.notBlank(pkName)) {
            return pkName;
        } else {
            return "id";
        }
    }
}
