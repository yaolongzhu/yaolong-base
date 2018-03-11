package yaolong.base.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import yaolong.base.ar.Model;
import yaolong.base.ar.Page;
import yaolong.base.ar.Record;

/**
 * @author
 */
public class Result {
    
    private String code = ReturnCode.SUCCESS;

    private String msg = ReturnCode.MSG_SUCCESS_CN;

    private Map<String, Object> data = new HashMap<String, Object>();

    /**
     * 
     */
    public Result() {
    }

    /**
     * @param code
     */
    public Result(String code) {
        this.code = code;
    }

    /**
     * @param code
     * @param msg
     */
    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * @param code
     * @param msg
     * @param data
     */
    public Result(String code, String msg, Map<String, Object> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * @return
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * @param data
     */
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * 判断data类型 支持Map，Model，Record List<Model>,List<Record> Page<Model>,Page
     * <Record> (如明确数据类型，可采用对应类型方法)
     * 
     * @param data
     */
    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    public void setData(Object data) {
        if (data instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) data;
            this.setData(map);
        } else if (data instanceof Model) {
            Model m = (Model) data;
            this.addModel(m);
        } else if (data instanceof Record) {
            Record r = (Record) data;
            this.addRecord(r);
        } else if (data instanceof List) {
            List list = (List) data;
            addList(list);
        } else if (data instanceof Page) {
            Page page = (Page) data;
            addPageInfo(page);
            List list = page.getList();
            addList(list);
        }
    }

    /**
     * 判断data类型 支持Map，Model，Record List<Model>,List<Record> Page<Model>,Page
     * <Record> (如明确数据类型，可采用对应类型方法)
     * 
     * @param data
     */
    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    public void setData(String key, Object data) {
        if (data instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) data;
            this.setData(map);
        } else if (data instanceof Model) {
            Model m = (Model) data;
            this.addModel(m);
        } else if (data instanceof Record) {
            Record r = (Record) data;
            this.addRecord(r);
        } else if (data instanceof List) {
            List list = (List) data;
            addList(key, list);
        } else if (data instanceof Page) {
            Page page = (Page) data;
            addPageInfo(page);
            List list = page.getList();
            addList(key, list);
        }
    }

    /**
     * 传入ModelList或RecordList，判断后放入data
     * 
     * @param list
     */
    @SuppressWarnings({
        "rawtypes", "unchecked"
    })
    private void addList(List list) {
        if (list != null && list.size() > 0) {
            Object obj = list.get(0);
            if (obj instanceof Model) {
                this.addModelList("info", list);
            } else if (obj instanceof Record) {
                this.addRecordList("info", list);
            } else {
                this.addObj("info", list);
            }
        }
    }

    /**
     * 传入ModelList或RecordList，判断后放入data
     * 
     * @param list
     */
    @SuppressWarnings({
        "rawtypes", "unchecked"
    })
    private void addList(String key, List list) {
        if (list != null && list.size() > 0) {
            Object obj = list.get(0);
            if (obj instanceof Model) {
                this.addModelList(key, list);
            } else if (obj instanceof Record) {
                this.addRecordList(key, list);
            } else {
                this.addObj(key, list);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void addPageInfo(Page page) {
        this.data.put("pageNumber", page.getPageNumber());
        this.data.put("pageSize", page.getPageSize());
        this.data.put("totalPage", page.getTotalPage());
        this.data.put("totalRow", page.getTotalRow());
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public Object put(String key, Object value) {
        return data.put(key, value);
    }

    /**
     * @param key
     * @param value
     * @return
     */
    public Object addObj(String key, Object value) {
        return data.put(key, value);
    }

    /**
     * @param key
     * @param list
     */
    @SuppressWarnings({
        "rawtypes", "unchecked"
    })
    public void addModelList(String key, List<?> list) {
        List<Map<String, Object>> modelList = new ArrayList<Map<String, Object>>();
        List<Model> models = (List<Model>) list;
        for (Model m: models) {
            modelList.add(m.toRecord().getColumns());
        }
        data.put(key, modelList);
    }

    /**
     * @param key
     * @param list
     */
    public void addRecordList(String key, List<Record> list) {
        List<Map<String, Object>> modelList = new ArrayList<Map<String, Object>>();
        for (Record r: list) {
            modelList.add(r.getColumns());
        }
        data.put(key, modelList);
    }

    /**
     * @param key
     * @param page
     */
    public void addPageRecored(String key, Page<Record> page) {
        addPageInfo(page);
        List<Record> list = page.getList();
        addRecordList(key, list);
    }

    /**
     * @param key
     * @param page
     */
    public void addPageModel(String key, Page<?> page) {
        addPageInfo(page);
        List<?> list = page.getList();
        addModelList(key, list);
    }

    /**
     * @param key
     * @param map
     */
    public void addChildren(String key, Map<String, Object> map) {
        Map<String, Object> children = new HashMap<String, Object>();
        for (Entry<String, Object> entry: map.entrySet()) {
            Object val = entry.getValue();
            if (val instanceof Map) {
                childrenMap(key, val, children);
            } else {
                children(key, val, children);
            }
        }
        data.put(key, children);
    }

    /**
     * @param key
     * @param val
     * @param children
     * @return
     */
    @SuppressWarnings("rawtypes")
    private Map<String, Object> children(String key, Object val, Map<String, Object> children) {
        if (val instanceof Record) {
            Record record = (Record) val;
            children.put(key, record.getColumns());
        } else if (val instanceof Model) {
            Model model = (Model) val;
            children.put(key, model.toRecord().getColumns());
        }
        return children;
    }

    /**
     * @param key
     * @param value
     * @param children
     * @return
     */
    @SuppressWarnings("unchecked")
    private Map<String, Object> childrenMap(String key, Object value, Map<String, Object> children) {
        Map<String, Object> child = new HashMap<String, Object>();
        if (value instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) value;
            for (Entry<String, Object> entry: map.entrySet()) {
                childrenMap(key, entry.getValue(), child);
            }
        } else {
            children(key, value, child);
        }
        children.put(key, child);
        return children;
    }

    /**
     * @param model
     */
    @SuppressWarnings("rawtypes")
    public void addModel(Model model) {
        data.putAll(model.toRecord().getColumns());
    }

    /**
     * @param record
     */
    public void addRecord(Record record) {
        data.putAll(record.getColumns());
    }

    /**
     * @param map
     */
    public void putAll(Map<? extends String, ? extends Object> map) {
        data.putAll(map);
    }

    /**
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
