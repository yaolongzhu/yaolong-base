package yaolong.base.common.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import yaolong.base.common.exception.BaseException;

/**
 * @author
 */
public class JsonUtils {

    /**
     * 对序列化的Long类型进行特殊处理,避免位数过大导致和js精度的丢失,只用于向页面发送json数据时使用
     */
    private static final ObjectSerializer longSerializer = new BaseSerializer();

    private static final SerializeConfig SERIALIZE_CONFIG = new SerializeConfig();

    static {
        SERIALIZE_CONFIG.put(Long.class, longSerializer);
        SERIALIZE_CONFIG.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 对Long型兼容js的json串
     * <p>
     *
     * @param object
     *            对象
     * @return json字符串
     */
    public static String toCompatibleJSON(Object object, String format) {
        SerializeWriter out = new SerializeWriter();
        try {
            // 此处必须new一个SerializeConfig,防止修改默认的配置
            if (format != null) {
                JSONSerializer serializer = new JSONSerializer(out, new SerializeConfig());
                serializer.getMapping().put(Long.class, longSerializer);
                serializer.getMapping().put(Date.class, new SimpleDateFormatSerializer(format));
            } else {
                JSONSerializer serializer = new JSONSerializer(out, SERIALIZE_CONFIG);
                serializer.write(object);
            }
            return out.toString();
        } finally {
            out.close();
        }
    }

    public static String toCompatibleJSON(Object object) {
        return toCompatibleJSON(object, null);
    }

    /**
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {
            return JSON.toJSONString(object, SerializerFeature.WriteNullStringAsEmpty,
                    SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            throw new BaseException("JSON数据转换时出现错误:" + object.getClass().getName(), e);
        }
    }

    /**
     * @param object
     * @param ignoreNull
     * @return
     */
    public static String toJson(Object object, boolean ignoreNull) {
        try {
            if (!ignoreNull) {
                return JSON.toJSONString(object, SerializerFeature.WriteMapNullValue,
                        SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
            } else {
                return JSON.toJSONString(object, SerializerFeature.WriteNullStringAsEmpty,
                        SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
            }
        } catch (Exception e) {
            throw new BaseException("JSON数据转换时出现错误:" + object.getClass().getName(), e);
        }
    }

    /**
     * @param json
     * @return
     */
    public static Map<String, Object> parseHashMap(String json) {
        try {
            Map<String, Object> data = JSON.parseObject(json);
            return data;
        } catch (Exception e) {
            throw new BaseException("解析JSON字符串时发生错误。", e);
        }
    }

    /**
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Map<String, Object>> parseJsonMap(String json) {
        try {
            return JSON.parseObject(json, Map.class);
        } catch (Exception e) {
            throw new BaseException("解析JSON字符串时发生错误。", e);
        }
    }

    /**
     * @param json
     * @param type
     * @return
     */
    public static <T> T parseJson(String json, Class<T> type) {
        try {
            return JSON.parseObject(json, type);
        } catch (Exception e) {
            throw new BaseException("解析JSON字符串时发生错误。", e);
        }
    }

    /**
     * @param json
     * @return
     */
    public static JSONObject parseJson(String json) {
        try {
            return JSON.parseObject(json);
        } catch (Exception e) {
            throw new BaseException("解析JSON字符串时发生错误。", e);
        }
    }

    /**
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> parseJsonList(String json) {
        try {
            return JSON.parseObject(json, List.class);
        } catch (Exception e) {
            throw new BaseException("解析JSON字符串时发生错误。", e);
        }
    }

    /**
     * @param json
     * @param beanClass
     * @return
     */
    public static <T> List<T> parseJsonList(String json, Class<T> beanClass) {
        try {
            return (List<T>) JSONArray.parseArray(json, beanClass);
        } catch (Exception e) {
            throw new BaseException("解析JSON字符串时发生错误。", e);
        }
    }

    /**
     * 适用格式为：{"key1":["k1":"v1","k2":"v2"]，"key2":["k3":"v3","k4":"v4"]}
     * 
     * @param json
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, List<Map<String, Object>>> parseMapList(String json) {
        try {
            return (Map<String, List<Map<String, Object>>>) JSON.parseObject(json, HashMap.class);
        } catch (Exception e) {
            throw new BaseException("解析JSON字符串时发生错误。", e);
        }
    }

    public static void main(String[] args) {
        // String json =
        // "{\"success\":true,\"A\":{\"address\":\"address2\",\"name\":\"haha2\",\"id\":\"2\",\"email\":\"email2\"},"
        // +
        // "\"B\":{\"address\":\"address\",\"name\":\"haha\",\"id\":\"1\",\"email\":\"email\"}}";
        // Map<String, Object> map = JsonUtils.parseHashMap(json);
        // for (Entry<String, Object> entry : map.entrySet()) {
        // System.out.println(entry.getKey());
        // System.out.println(entry.getValue());
        // if (entry.getValue() instanceof Map) {
        // System.out.println("key2:" + entry.getKey());
        // System.out.println("value2:" + entry.getValue());
        // }
        // }
        Map<String, Object> p = new HashMap<String, Object>();
        List<Object> list = new ArrayList<Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 123456789012345L);
        map.put("abc", null);
        map.put("time", new Date());
        list.add(map);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("id", 123456789012345L);
        map2.put("abc", null);
        map2.put("time", new Date());
        list.add(map2);
        p.put("test", list);
        // p.put("user", map);
        System.out.println(toCompatibleJSON(p));
    }
}
