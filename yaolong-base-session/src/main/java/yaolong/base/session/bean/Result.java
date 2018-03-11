package yaolong.base.session.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 */
public class Result {

    private String code = "13";

    private String msg = "重复请求";

    private Map<String, Object> data = new HashMap<String, Object>();

    public Result() {
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Object putObj(String key, Object value) {
        return data.put(key, value);
    }

}
