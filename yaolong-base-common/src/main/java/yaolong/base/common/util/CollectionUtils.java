package yaolong.base.common.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 */
public class CollectionUtils {

    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> copyByKeys(Map<K, V> map, K... keys) {
        Map<K, V> res = new HashMap<K, V>();
        Arrays.stream(keys).forEach((k) -> {
            V v = map.get(k);
            if (v != null)
                res.put(k, v);
        });
        return res;
    }

}
