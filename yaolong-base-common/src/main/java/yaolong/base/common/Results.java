package yaolong.base.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import yaolong.base.ar.Model;
import yaolong.base.ar.Record;

/**
 * @author
 */
public class Results {
    
    private String code = ReturnCode.SUCCESS;

    private String msg = ReturnCode.MSG_SUCCESS_CN;

    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

    public Results() {
    }

    public Results(String code) {
        this.code = code;
    }

    public Results(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Results(String code, String msg, List<Map<String, Object>> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @SuppressWarnings({
        "rawtypes", "unchecked"
    })
    public void addModelList(List<?> list) {
        if (list != null && list.size() > 0) {
            List<Model> models = (List<Model>) list;
            for (Model m: models) {
                data.add(m.toRecord().getColumns());
            }
        }
    }

    public void addRecordList(List<Record> list) {
        if (list != null && list.size() > 0) {
            for (Record r: list) {
                data.add(r.getColumns());
            }
        }
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

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

}
