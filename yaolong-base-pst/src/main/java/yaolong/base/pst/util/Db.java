package yaolong.base.pst.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import yaolong.base.ar.Config;
import yaolong.base.ar.DbBase;
import yaolong.base.ar.IAtom;
import yaolong.base.ar.ICallback;
import yaolong.base.ar.Model;
import yaolong.base.ar.Page;
import yaolong.base.ar.Record;
import yaolong.base.ar.Table;
import yaolong.base.ar.TableMapping;
import yaolong.base.ar.kit.StrKit;
import yaolong.base.id.IdWorker;
import yaolong.base.inf.Audit;
import yaolong.base.pst.annotation.Tab;

/**
 * @author LUOGX 数据库操作类
 */
public class Db extends DbBase {

    /**
     * 默认PK
     */
    private static final String DEFAULT_PK = "id";

    /**
     * ID生成器
     */
    private static Map<String, IdWorker> idWokers = new HashMap<String, IdWorker>();

    /**
     * key:tableName value:column,column,....
     */
    private static Map<String, String[]> tableAttrs = new HashMap<String, String[]>();

    /**
     * @param m
     * @param param
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Record> query(Model m, Map<String, Object> param) {
        return query(getTableName(m), "*", param);
    }

    /**
     * @param m
     * @param param
     * @param desc
     * @param order
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Record> query(Model m, Map<String, Object> param, boolean desc, String... order) {
        return query(getTableName(m), "*", param, desc, order);
    }

    /**
     * @param m
     * @param param
     * @param order
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Record> query(Model m, Map<String, Object> param, String order) {
        return query(getTableName(m), "*", param, false, order);
    }

    /**
     * @param m
     * @param param
     * @param desc
     * @param order
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Record> query(Model m, Map<String, Object> param, boolean desc, String order) {
        return query(getTableName(m), "*", param, desc, order);
    }

    /**
     * @param m
     * @param columns
     * @param param
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Record> query(Model m, String columns, Map<String, Object> param) {
        return query(getTableName(m), columns, param);
    }

    /**
     * @param m
     * @param columns
     * @param param
     * @param desc
     * @param order
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Record> query(Model m, String columns, Map<String, Object> param, boolean desc,
            String... order) {
        return query(getTableName(m), columns, param, desc, order);
    }

    /**
     * @param m
     * @param columns
     * @param param
     * @param order
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Record> query(Model m, String columns, Map<String, Object> param, String order) {
        return query(getTableName(m), columns, param, false, order);
    }

    /**
     * @param m
     * @param columns
     * @param param
     * @param desc
     * @param order
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Record> query(Model m, String columns, Map<String, Object> param, boolean desc, String order) {
        return query(getTableName(m), columns, param, desc, order);
    }

    /**
     * @param tableName
     * @param param
     * @return
     */
    public static List<Record> query(String tableName, Map<String, Object> param) {
        return query(tableName, "*", param);
    }

    /**
     * @param tableName
     * @param param
     * @param desc
     * @param order
     * @return
     */
    public static List<Record> query(String tableName, Map<String, Object> param, boolean desc, String... order) {
        return query(tableName, "*", param, desc, order);
    }

    /**
     * @param tableName
     * @param param
     * @param order
     * @return
     */
    public static List<Record> query(String tableName, Map<String, Object> param, String order) {
        return query(tableName, "*", param, false, order);
    }

    /**
     * @param tableName
     * @param param
     * @param desc
     * @param order
     * @return
     */
    public static List<Record> query(String tableName, Map<String, Object> param, boolean desc, String order) {
        return query(tableName, "*", param, desc, order);
    }

    /**
     * @param tableName
     * @param columns
     * @param param
     * @return
     */
    public static List<Record> query(String tableName, String columns, Map<String, Object> param) {
        StringBuilder sql = new StringBuilder("select " + columns + " from " + tableName);
        int size = param.size();
        if (size > 0) {
            sql.append(" where ");
            Object[] objs = new Object[size];
            Set<Entry<String, Object>> set = param.entrySet();
            Iterator<Entry<String, Object>> ite = set.iterator();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    sql.append(" and ");
                }
                Entry<String, Object> entry = ite.next();
                sql.append("`").append(entry.getKey()).append("` = ?");
                objs[i] = entry.getValue();
            }
            return find(sql.toString(), objs);
        }
        return find(sql.toString());
    }

    /**
     * @param tableName
     * @param columns
     * @param param
     * @param desc
     * @param order
     * @return
     */
    public static List<Record> query(String tableName, String columns, Map<String, Object> param, boolean desc,
            String... order) {
        StringBuilder sql = new StringBuilder("select " + columns + " from " + tableName);
        int size = param.size();
        if (size > 0) {
            sql.append(" where ");
            Object[] objs = new Object[size];
            Set<Entry<String, Object>> set = param.entrySet();
            Iterator<Entry<String, Object>> ite = set.iterator();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    sql.append(" and ");
                }
                Entry<String, Object> entry = ite.next();
                sql.append("`").append(entry.getKey()).append("` = ?");
                objs[i] = entry.getValue();
            }
            appendOrder(sql, desc, order);
            return find(sql.toString(), objs);
        }
        appendOrder(sql, desc, order);
        return find(sql.toString());
    }

    /**
     * @param tableName
     * @param columns
     * @param param
     * @param order
     * @return
     */
    public static List<Record> query(String tableName, String columns, Map<String, Object> param, String order) {
        return query(tableName, columns, param, false, order);
    }

    /**
     * @param tableName
     * @param columns
     * @param param
     * @param desc
     * @param order
     * @return
     */
    public static List<Record> query(String tableName, String columns, Map<String, Object> param, boolean desc,
            String order) {
        StringBuilder sql = new StringBuilder("select " + columns + " from " + tableName);
        int size = param.size();
        if (size > 0) {
            sql.append(" where ");
            Object[] objs = new Object[size];
            Set<Entry<String, Object>> set = param.entrySet();
            Iterator<Entry<String, Object>> ite = set.iterator();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    sql.append(" and ");
                }
                Entry<String, Object> entry = ite.next();
                sql.append("`").append(entry.getKey()).append("` = ?");
                objs[i] = entry.getValue();
            }
            appendOrder(sql, desc, order);
            return find(sql.toString(), objs);
        }
        appendOrder(sql, desc, order);
        return find(sql.toString());
    }

    /**
     * 分页查询
     * 
     * @param m
     * @param param
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Page<Record> query(Model m, Map<String, Object> param, int pageNumber, int pageSize) {
        return query(getTableName(m), "*", param, pageNumber, pageSize);
    }

    /**
     * @param m
     * @param param
     * @param pageNumber
     * @param pageSize
     * @param desc
     * @param order
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Page<Record> query(Model m, Map<String, Object> param, int pageNumber, int pageSize, boolean desc,
            String... order) {
        return query(getTableName(m), "*", param, pageNumber, pageSize, desc, order);
    }

    /**
     * @param m
     * @param param
     * @param pageNumber
     * @param pageSize
     * @param desc
     * @param order
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Page<Record> query(Model m, Map<String, Object> param, int pageNumber, int pageSize, boolean desc,
            String order) {
        return query(getTableName(m), "*", param, pageNumber, pageSize, desc, order);
    }

    /**
     * 分页查询
     * 
     * @param m
     * @param columns
     * @param param
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Page<Record> query(Model m, String columns, Map<String, Object> param, int pageNumber, int pageSize) {
        return query(getTableName(m), columns, param, pageNumber, pageSize);
    }

    /**
     * @param m
     * @param columns
     * @param param
     * @param pageNumber
     * @param pageSize
     * @param desc
     * @param order
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Page<Record> query(Model m, String columns, Map<String, Object> param, int pageNumber, int pageSize,
            boolean desc, String... order) {
        return query(getTableName(m), columns, param, pageNumber, pageSize, desc, order);
    }

    /**
     * 分页查询
     * 
     * @param tableName
     * @param param
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static Page<Record> query(String tableName, Map<String, Object> param, int pageNumber, int pageSize) {
        return query(tableName, "*", param, pageNumber, pageSize);
    }

    /**
     * @param tableName
     * @param param
     * @param pageNumber
     * @param pageSize
     * @param desc
     * @param order
     * @return
     */
    public static Page<Record> query(String tableName, Map<String, Object> param, int pageNumber, int pageSize,
            boolean desc, String... order) {
        return query(tableName, "*", param, pageNumber, pageSize, desc, order);
    }

    /**
     * @param tableName
     * @param param
     * @param pageNumber
     * @param pageSize
     * @param desc
     * @param order
     * @return
     */
    public static Page<Record> query(String tableName, Map<String, Object> param, int pageNumber, int pageSize,
            boolean desc, String order) {
        return query(tableName, "*", param, pageNumber, pageSize, desc, order);
    }

    /**
     * 分页查询
     * 
     * @param tableName
     * @param columns
     * @param param
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static Page<Record> query(String tableName, String columns, Map<String, Object> param, int pageNumber,
            int pageSize) {
        String select = "select " + columns;
        StringBuilder sql = new StringBuilder(" from " + tableName);
        if (param != null && (param.containsKey("pageNumber") || param.containsKey("pageSize"))) {
            param.remove("pageNumber");
            param.remove("pageSize");
        }
        int size = param.size();
        if (size > 0) {
            sql.append(" where ");
            Object[] objs = new Object[size];
            Set<Entry<String, Object>> set = param.entrySet();
            Iterator<Entry<String, Object>> ite = set.iterator();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    sql.append(" and ");
                }
                Entry<String, Object> entry = ite.next();
                // sql.append("`").append(entry.getKey()).append("` = ?");
                sql.append(entry.getKey() + " = ?");
                objs[i] = entry.getValue();
            }
            return paginate(pageNumber, pageSize, select, sql.toString(), objs);
        }
        return paginate(pageNumber, pageSize, select, sql.toString());
    }

    /**
     * @param tableName
     * @param columns
     * @param param
     * @param pageNumber
     * @param pageSize
     * @param desc
     * @param order
     * @return
     */
    public static Page<Record> query(String tableName, String columns, Map<String, Object> param, int pageNumber,
            int pageSize, boolean desc, String... order) {
        String select = "select " + columns;
        StringBuilder sql = new StringBuilder(" from " + tableName);
        if (param != null && (param.containsKey("pageNumber") || param.containsKey("pageSize"))) {
            param.remove("pageNumber");
            param.remove("pageSize");
        }
        int size = param.size();
        if (size > 0) {
            sql.append(" where ");
            Object[] objs = new Object[size];
            Set<Entry<String, Object>> set = param.entrySet();
            Iterator<Entry<String, Object>> ite = set.iterator();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    sql.append(" and ");
                }
                Entry<String, Object> entry = ite.next();
                // sql.append("`").append(entry.getKey()).append("` = ?");
                sql.append(entry.getKey() + " = ?");
                objs[i] = entry.getValue();
            }
            appendOrder(sql, desc, order);
            return paginate(pageNumber, pageSize, select, sql.toString(), objs);
        }
        appendOrder(sql, desc, order);
        return paginate(pageNumber, pageSize, select, sql.toString());
    }

    /**
     * @param tableName
     * @param columns
     * @param param
     * @param pageNumber
     * @param pageSize
     * @param desc
     * @param order
     * @return
     */
    public static Page<Record> query(String tableName, String columns, Map<String, Object> param, int pageNumber,
            int pageSize, boolean desc, String order) {
        String select = "select " + columns;
        StringBuilder sql = new StringBuilder(" from " + tableName);
        if (param != null && (param.containsKey("pageNumber") || param.containsKey("pageSize"))) {
            param.remove("pageNumber");
            param.remove("pageSize");
        }
        int size = param.size();
        if (size > 0) {
            sql.append(" where ");
            Object[] objs = new Object[size];
            Set<Entry<String, Object>> set = param.entrySet();
            Iterator<Entry<String, Object>> ite = set.iterator();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    sql.append(" and ");
                }
                Entry<String, Object> entry = ite.next();
                // sql.append("`").append(entry.getKey()).append("` = ?");
                sql.append(entry.getKey() + " = ?");
                objs[i] = entry.getValue();
            }
            appendOrder(sql, desc, order);
            return paginate(pageNumber, pageSize, select, sql.toString(), objs);
        }
        appendOrder(sql, desc, order);
        return paginate(pageNumber, pageSize, select, sql.toString());
    }

    /**
     * 不支持查询条件中字段在多张表中重名
     * 
     * @param tableName
     * @param columns
     * @param joinSql
     * @param param
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static Page<Record> query(String tableName, String columns, String joinSql, Map<String, Object> param,
            int pageNumber, int pageSize) {
        String select = "select " + columns;
        StringBuilder sql = new StringBuilder(" from " + tableName);
        if (param != null && (param.containsKey("pageNumber") || param.containsKey("pageSize"))) {
            param.remove("pageNumber");
            param.remove("pageSize");
        }
        int size = param.size();
        if (size > 0) {
            sql.append(" where " + joinSql);
            Object[] objs = new Object[size];
            Set<Entry<String, Object>> set = param.entrySet();
            Iterator<Entry<String, Object>> ite = set.iterator();
            for (int i = 0; i < size; i++) {
                sql.append(" and ");
                Entry<String, Object> entry = ite.next();
                sql.append(entry.getKey()).append(" = ?");
                objs[i] = entry.getValue();
            }
            return paginate(pageNumber, pageSize, select, sql.toString(), objs);
        }
        return paginate(pageNumber, pageSize, select, sql.toString());
    }

    /**
     * @param tableName
     * @param columns
     * @param joinSql
     * @param param
     * @param pageNumber
     * @param pageSize
     * @param desc
     * @param order
     * @return
     */
    public static Page<Record> query(String tableName, String columns, String joinSql, Map<String, Object> param,
            int pageNumber, int pageSize, boolean desc, String... order) {
        String select = "select " + columns;
        StringBuilder sql = new StringBuilder(" from " + tableName);
        if (param != null && (param.containsKey("pageNumber") || param.containsKey("pageSize"))) {
            param.remove("pageNumber");
            param.remove("pageSize");
        }
        int size = param.size();
        if (size > 0) {
            sql.append(" where " + joinSql);
            Object[] objs = new Object[size];
            Set<Entry<String, Object>> set = param.entrySet();
            Iterator<Entry<String, Object>> ite = set.iterator();
            for (int i = 0; i < size; i++) {
                sql.append(" and ");
                Entry<String, Object> entry = ite.next();
                sql.append(entry.getKey()).append(" = ?");
                objs[i] = entry.getValue();
            }
            appendOrder(sql, desc, order);
            return paginate(pageNumber, pageSize, select, sql.toString(), objs);
        }
        appendOrder(sql, desc, order);
        return paginate(pageNumber, pageSize, select, sql.toString());
    }

    /**
     * 两张表关联查询
     * 
     * @param tableName1
     * @param columns1
     * @param param1
     * @param tableName2
     * @param columns2
     * @param param2
     * @param joinSql
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static Page<Record> query(String tableName1, String columns1, Map<String, Object> param1, String tableName2,
            String columns2, Map<String, Object> param2, String joinSql, int pageNumber, int pageSize) {
        StringBuilder select = new StringBuilder("select ");
        String[] tcs1 = columns1.split(",");
        for (int i = 0; i < tcs1.length; i++) {
            select.append(tableName1 + "." + tcs1[i] + ",");
        }
        String[] tcs2 = columns2.split(",");
        if (tcs2.length > 1) {
            for (int i = 0; i < tcs2.length; i++) {
                select.append(tableName2 + "." + tcs2[i]);
                if (i < tcs2.length - 1) {
                    select.append(",");
                }
            }
        } else {
            select.append(tableName2 + "." + columns2);
        }
        StringBuilder sql = new StringBuilder(" from " + tableName1 + "," + tableName2);
        int size1 = 0;
        if (param1 != null) {
            if (param1.containsKey("pageNumber") || param1.containsKey("pageSize")) {
                param1.remove("pageNumber");
                param1.remove("pageSize");
            }
            size1 = param1.size();
        }
        int size2 = 0;
        if (param2 != null) {
            if (param2.containsKey("pageNumber") || param2.containsKey("pageSize")) {
                param2.remove("pageNumber");
                param2.remove("pageSize");
            }
            size2 = param2.size();
        }
        int size = size1 + size2;
        if (size > 0) {
            sql.append(" where " + joinSql);
            Object[] objs = new Object[size];
            if (param1 != null) {
                Set<Entry<String, Object>> set1 = param1.entrySet();
                Iterator<Entry<String, Object>> ite1 = set1.iterator();
                for (int i = 0; i < size1; i++) {
                    sql.append(" and ");
                    Entry<String, Object> entry = ite1.next();
                    sql.append(tableName1 + "." + entry.getKey()).append(" = ?");
                    objs[i] = entry.getValue();
                }
            }
            if (param2 != null) {
                Set<Entry<String, Object>> set2 = param2.entrySet();
                Iterator<Entry<String, Object>> ite2 = set2.iterator();
                for (int i = 0; i < size2; i++) {
                    sql.append(" and ");
                    Entry<String, Object> entry = ite2.next();
                    sql.append(tableName2 + "." + entry.getKey()).append(" = ?");
                    objs[i] = entry.getValue();
                }
            }
            return paginate(pageNumber, pageSize, select.toString(), sql.toString(), objs);
        }
        return paginate(pageNumber, pageSize, select.toString(), sql.toString());
    }

    /**
     * @param tableName1
     * @param columns1
     * @param param1
     * @param tableName2
     * @param columns2
     * @param param2
     * @param joinSql
     * @param pageNumber
     * @param pageSize
     * @param desc
     * @param order
     * @return
     */
    public static Page<Record> query(String tableName1, String columns1, Map<String, Object> param1, String tableName2,
            String columns2, Map<String, Object> param2, String joinSql, int pageNumber, int pageSize, boolean desc,
            String... order) {
        StringBuilder select = new StringBuilder("select ");
        String[] tcs1 = columns1.split(",");
        for (int i = 0; i < tcs1.length; i++) {
            select.append(tableName1 + "." + tcs1[i] + ",");
        }
        String[] tcs2 = columns2.split(",");
        if (tcs2.length > 1) {
            for (int i = 0; i < tcs2.length; i++) {
                select.append(tableName2 + "." + tcs2[i]);
                if (i < tcs2.length - 1) {
                    select.append(",");
                }
            }
        } else {
            select.append(tableName2 + "." + columns2);
        }
        StringBuilder sql = new StringBuilder(" from " + tableName1 + "," + tableName2);
        int size1 = 0;
        if (param1 != null) {
            if (param1.containsKey("pageNumber") || param1.containsKey("pageSize")) {
                param1.remove("pageNumber");
                param1.remove("pageSize");
            }
            size1 = param1.size();
        }
        int size2 = 0;
        if (param2 != null) {
            if (param2.containsKey("pageNumber") || param2.containsKey("pageSize")) {
                param2.remove("pageNumber");
                param2.remove("pageSize");
            }
            size2 = param2.size();
        }
        int size = size1 + size2;
        if (size > 0) {
            sql.append(" where " + joinSql);
            Object[] objs = new Object[size];
            if (param1 != null) {
                Set<Entry<String, Object>> set1 = param1.entrySet();
                Iterator<Entry<String, Object>> ite1 = set1.iterator();
                for (int i = 0; i < size1; i++) {
                    sql.append(" and ");
                    Entry<String, Object> entry = ite1.next();
                    sql.append(tableName1 + "." + entry.getKey()).append(" = ?");
                    objs[i] = entry.getValue();
                }
            }
            if (param2 != null) {
                Set<Entry<String, Object>> set2 = param2.entrySet();
                Iterator<Entry<String, Object>> ite2 = set2.iterator();
                for (int i = 0; i < size2; i++) {
                    sql.append(" and ");
                    Entry<String, Object> entry = ite2.next();
                    sql.append(tableName2 + "." + entry.getKey()).append(" = ?");
                    objs[i] = entry.getValue();
                }
            }
            appendOrder(sql, desc, order);
            return paginate(pageNumber, pageSize, select.toString(), sql.toString(), objs);
        }
        appendOrder(sql, desc, order);
        return paginate(pageNumber, pageSize, select.toString(), sql.toString());
    }

    /**
     * 模糊查询，只支持varchar类型
     * 
     * @param m
     * @param param
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Record> queryLike(Model m, Map<String, Object> param) {
        return queryLike(getTableName(m), "*", param);
    }

    /**
     * 模糊分页查询，只支持varchar类型
     * 
     * @param m
     * @param param
     * @param desc
     * @param order
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Record> queryLike(Model m, Map<String, Object> param, boolean desc, String... order) {
        return queryLike(getTableName(m), "*", param, desc, order);
    }

    /**
     * 模糊查询，只支持varchar类型
     * 
     * @param m
     * @param columns
     * @param param
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Record> queryLike(Model m, String columns, Map<String, Object> param) {
        return queryLike(getTableName(m), columns, param);
    }

    /**
     * 模糊查询，只支持varchar类型
     * 
     * @param m
     * @param columns
     * @param param
     * @param desc
     * @param order
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Record> queryLike(Model m, String columns, Map<String, Object> param, boolean desc,
            String... order) {
        return queryLike(getTableName(m), columns, param, desc, order);
    }

    /**
     * 模糊查询，只支持varchar类型
     * 
     * @param tableName
     * @param param
     * @return
     */
    public static List<Record> queryLike(String tableName, Map<String, Object> param) {
        return queryLike(tableName, "*", param);
    }

    /**
     * 模糊分页查询，只支持varchar类型
     * 
     * @param tableName
     * @param param
     * @param desc
     * @param order
     * @return
     */
    public static List<Record> queryLike(String tableName, Map<String, Object> param, boolean desc, String... order) {
        return queryLike(tableName, "*", param, desc, order);
    }

    /**
     * 模糊查询，只支持varchar类型
     * 
     * @param tableName
     * @param columns
     * @param param
     * @return
     */
    public static List<Record> queryLike(String tableName, String columns, Map<String, Object> param) {
        StringBuilder sql = new StringBuilder("select " + columns + " from " + tableName);
        int size = param.size();
        if (size > 0) {
            sql.append(" where ");
            Object[] objs = new Object[size];
            Set<Entry<String, Object>> set = param.entrySet();
            Iterator<Entry<String, Object>> ite = set.iterator();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    sql.append(" and ");
                }
                Entry<String, Object> entry = ite.next();
                sql.append("`").append(entry.getKey()).append("` like ?");
                objs[i] = "%" + entry.getValue() + "%";
            }
            return find(sql.toString(), objs);
        }
        return find(sql.toString());
    }

    /**
     * 模糊分页查询，只支持varchar类型
     * 
     * @param tableName
     * @param columns
     * @param param
     * @param desc
     * @param order
     * @return
     */
    public static List<Record> queryLike(String tableName, String columns, Map<String, Object> param, boolean desc,
            String... order) {
        StringBuilder sql = new StringBuilder("select " + columns + " from " + tableName);
        int size = param.size();
        if (size > 0) {
            sql.append(" where ");
            Object[] objs = new Object[size];
            Set<Entry<String, Object>> set = param.entrySet();
            Iterator<Entry<String, Object>> ite = set.iterator();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    sql.append(" and ");
                }
                Entry<String, Object> entry = ite.next();
                sql.append("`").append(entry.getKey()).append("` like ?");
                objs[i] = "%" + entry.getValue() + "%";
            }
            appendOrder(sql, desc, order);
            return find(sql.toString(), objs);
        }
        appendOrder(sql, desc, order);
        return find(sql.toString());
    }

    /**
     * 模糊分页查询，只支持varchar类型
     * 
     * @param m
     * @param param
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Page<Record> queryLike(Model m, Map<String, Object> param, int pageNumber, int pageSize) {
        return queryLike(getTableName(m), "*", param, pageNumber, pageSize);
    }

    /**
     * 模糊分页查询，只支持varchar类型
     * 
     * @param m
     * @param param
     * @param pageNumber
     * @param pageSize
     * @param desc
     * @param order
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Page<Record> queryLike(Model m, Map<String, Object> param, int pageNumber, int pageSize, boolean desc,
            String... order) {
        return queryLike(getTableName(m), "*", param, pageNumber, pageSize, desc, order);
    }

    /**
     * 模糊分页查询，只支持varchar类型
     * 
     * @param m
     * @param columns
     * @param param
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Page<Record> queryLike(Model m, String columns, Map<String, Object> param, int pageNumber,
            int pageSize) {
        return queryLike(getTableName(m), columns, param, pageNumber, pageSize);
    }

    /**
     * 模糊分页查询，只支持varchar类型
     * 
     * @param m
     * @param columns
     * @param param
     * @param pageNumber
     * @param pageSize
     * @param desc
     * @param order
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Page<Record> queryLike(Model m, String columns, Map<String, Object> param, int pageNumber,
            int pageSize, boolean desc, String... order) {
        return queryLike(getTableName(m), columns, param, pageNumber, pageSize, desc, order);
    }

    /**
     * 模糊分页查询，只支持varchar类型
     * 
     * @param tableName
     * @param param
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static Page<Record> queryLike(String tableName, Map<String, Object> param, int pageNumber, int pageSize) {
        return queryLike(tableName, "*", param, pageNumber, pageSize);
    }

    /**
     * 模糊分页查询，只支持varchar类型
     * 
     * @param tableName
     * @param param
     * @param pageNumber
     * @param pageSize
     * @param desc
     * @param order
     * @return
     */
    public static Page<Record> queryLike(String tableName, Map<String, Object> param, int pageNumber, int pageSize,
            boolean desc, String... order) {
        return queryLike(tableName, "*", param, pageNumber, pageSize, desc, order);
    }

    /**
     * 模糊分页查询，只支持varchar类型
     * 
     * @param tableName
     * @param columns
     * @param param
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static Page<Record> queryLike(String tableName, String columns, Map<String, Object> param, int pageNumber,
            int pageSize) {
        String select = "select " + columns;
        StringBuilder sql = new StringBuilder(" from " + tableName);
        if (param != null && (param.containsKey("pageNumber") || param.containsKey("pageSize"))) {
            param.remove("pageNumber");
            param.remove("pageSize");
        }
        int size = param.size();
        if (size > 0) {
            sql.append(" where ");
            Object[] objs = new Object[size];
            Set<Entry<String, Object>> set = param.entrySet();
            Iterator<Entry<String, Object>> ite = set.iterator();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    sql.append(" and ");
                }
                Entry<String, Object> entry = ite.next();
                sql.append("`").append(entry.getKey()).append("` like ?");
                objs[i] = "%" + entry.getValue() + "%";
            }
            return paginate(pageNumber, pageSize, select, sql.toString(), objs);
        }
        return paginate(pageNumber, pageSize, select, sql.toString());
    }

    /**
     * @param tableName
     * @param columns
     * @param param
     * @param pageNumber
     * @param pageSize
     * @param desc
     * @param order
     * @return
     */
    public static Page<Record> queryLike(String tableName, String columns, Map<String, Object> param, int pageNumber,
            int pageSize, boolean desc, String... order) {
        String select = "select " + columns;
        StringBuilder sql = new StringBuilder(" from " + tableName);
        if (param != null && (param.containsKey("pageNumber") || param.containsKey("pageSize"))) {
            param.remove("pageNumber");
            param.remove("pageSize");
        }
        int size = param.size();
        if (size > 0) {
            sql.append(" where ");
            Object[] objs = new Object[size];
            Set<Entry<String, Object>> set = param.entrySet();
            Iterator<Entry<String, Object>> ite = set.iterator();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    sql.append(" and ");
                }
                Entry<String, Object> entry = ite.next();
                sql.append("`").append(entry.getKey()).append("` like ?");
                objs[i] = "%" + entry.getValue() + "%";
            }
            appendOrder(sql, desc, order);
            return paginate(pageNumber, pageSize, select, sql.toString(), objs);
        }
        appendOrder(sql, desc, order);
        return paginate(pageNumber, pageSize, select, sql.toString());
    }

    /**
     * @param m
     * @param param
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Map<String, Object>> queryMap(Model m, Map<String, Object> param) {
        return converToMap(query(getTableName(m), "*", param));
    }

    /**
     * @param m
     * @param param
     * @param desc
     * @param order
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Map<String, Object>> queryMap(Model m, Map<String, Object> param, boolean desc,
            String... order) {
        return converToMap(query(getTableName(m), "*", param, desc, order));
    }

    /**
     * @param m
     * @param columns
     * @param param
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Map<String, Object>> queryMap(Model m, String columns, Map<String, Object> param) {
        return converToMap(query(getTableName(m), columns, param));
    }

    /**
     * @param m
     * @param columns
     * @param param
     * @param desc
     * @param order
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Map<String, Object>> queryMap(Model m, String columns, Map<String, Object> param, boolean desc,
            String... order) {
        return converToMap(query(getTableName(m), columns, param, desc, order));
    }

    /**
     * @param tableName
     * @param param
     * @return
     */
    public static List<Map<String, Object>> queryMap(String tableName, Map<String, Object> param) {
        return converToMap(query(tableName, "*", param));
    }

    /**
     * @param tableName
     * @param param
     * @param desc
     * @param order
     * @return
     */
    public static List<Map<String, Object>> queryMap(String tableName, Map<String, Object> param, boolean desc,
            String... order) {
        return converToMap(query(tableName, "*", param, desc, order));
    }

    /**
     * @param tableName
     * @param columns
     * @param param
     * @return
     */
    public static List<Map<String, Object>> queryMap(String tableName, String columns, Map<String, Object> param) {
        return converToMap(query(tableName, columns, param));
    }

    /**
     * @param tableName
     * @param columns
     * @param param
     * @param desc
     * @param order
     * @return
     */
    public static List<Map<String, Object>> queryMap(String tableName, String columns, Map<String, Object> param,
            boolean desc, String... order) {
        return converToMap(query(tableName, columns, param, desc, order));
    }

    /**
     * @param recordList
     * @return
     */
    public static List<Map<String, Object>> converToMap(List<Record> recordList) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (Record r: recordList) {
            list.add(r.getColumns());
        }
        return list;
    }

    /**
     * @param m
     * @param audit
     * @param data
     * @return
     */
    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    public static boolean save(Model m, Audit audit, Map<String, Object> data) {
        return m.put(initInsert(m, audit, data)).save();
    }

    /**
     * @param m
     * @param tableName
     * @param audit
     * @param data
     *            包含主键信息
     * @return
     */
    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    public static boolean save(Model m, String tableName, Audit audit, Map<String, Object> data) {
        return save(tableName, m.put(initInsert(m, audit, data)).toRecord());
    }

    /**
     * @param m
     * @param tableName
     * @param primaryKey
     *            Example:"user_id, role_id"
     * @param audit
     * @param data
     *            包含主键信息
     * @return
     */
    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    public static boolean save(Model m, String tableName, String primaryKey, Audit audit, Map<String, Object> data) {
        return save(tableName, primaryKey, m.put(initInsert(m, audit, data)).toRecord());
    }

    /**
     * 用于无审计信息表数据新增
     * 
     * @param m
     * @param data
     * @return
     */
    @SuppressWarnings({
        "rawtypes", "unchecked"
    })
    public static boolean save(Model m, Map<String, Object> data) {
        return initId(m, data).put(data).save();
    }

    /**
     * @param m
     * @param tableName
     * @param data
     *            包含主键信息
     * @return
     */
    @SuppressWarnings({
        "rawtypes", "unchecked"
    })
    public static boolean save(Model m, String tableName, Map<String, Object> data) {
        initId(m, data);
        return save(tableName, m.put(data).toRecord());
    }

    /**
     * @param m
     * @param tableName
     * @param primaryKey
     *            Example:"user_id, role_id"
     * @param data
     *            包含主键信息
     * @return
     */
    @SuppressWarnings({
        "rawtypes"
    })
    public static boolean save(Model m, String tableName, String primaryKey, Map<String, Object> data) {
        return save(tableName, primaryKey, m.put(initId(m, tableName, primaryKey, data)).toRecord());
    }

    /**
     * @param m
     * @param audit
     * @param data
     *            包含主键信息
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean update(Model m, Audit audit, Map<String, Object> data) {
        String tableName = getTableName(m);
        String primaryKey = getPrimaryKey(m);
        return update(tableName, primaryKey, m, audit, data);
    }

    /**
     * @param tableName
     * @param m
     * @param audit
     * @param data
     *            包含主键信息
     * @return
     */
    @SuppressWarnings({
        "rawtypes"
    })
    public static boolean update(String tableName, Model m, Audit audit, Map<String, Object> data) {
        String primaryKey = getPrimaryKey(m);
        Record record = putData(tableName, auditUpdate(m, audit, data), data);
        return update(tableName, primaryKey, record);
    }

    /**
     * @param tableName
     * @param primaryKey
     *            Example:"user_id, role_id"
     * @param m
     * @param audit
     * @param data
     * @return
     */
    @SuppressWarnings({
        "rawtypes"
    })
    public static boolean update(String tableName, String primaryKey, Model m, Audit audit, Map<String, Object> data) {
        Record record = putData(tableName, auditUpdate(m, audit, data), data);
        return update(tableName, primaryKey, record);
    }

    /**
     * 用于无审计信息表数据更新
     * 
     * @param m
     * @param data
     *            包含主键信息
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean update(Model m, Map<String, Object> data) {
        String tableName = getTableName(m);
        String primaryKey = getPrimaryKey(m);
        return update(tableName, primaryKey, m, data);
    }

    /**
     * 用于无审计信息表数据更新
     * 
     * @param tableName
     * @param m
     * @param data
     *            包含主键信息
     * @return
     */
    @SuppressWarnings({
        "rawtypes"
    })
    public static boolean update(String tableName, Model m, Map<String, Object> data) {
        String primaryKey = getPrimaryKey(m);
        Record record = putData(tableName, m, data);
        return update(tableName, primaryKey, record);
    }

    /**
     * @param tableName
     * @param primaryKey
     *            Example:"user_id, role_id"
     * @param m
     * @param data
     *            包含主键信息
     * @return
     */
    @SuppressWarnings({
        "rawtypes"
    })
    public static boolean update(String tableName, String primaryKey, Model m, Map<String, Object> data) {
        Record record = putData(tableName, m, data);
        return update(tableName, primaryKey, record);
    }

    /**
     * 直接转为Record
     * 
     * @param tableName
     * @param m
     * @param data
     * @return
     */
    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    public static Record putData(String tableName, Model m, Map<String, Object> data) {
        return m.put(data).toRecord();
    }

    /**
     * 去除多余字段
     * 
     * @param m
     * @param audit
     * @param data
     *            包含主键信息
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean updateAndKeep(Model m, Audit audit, Map<String, Object> data) {
        String tableName = getTableName(m);
        String primaryKey = getPrimaryKey(m);
        return updateAndKeep(tableName, primaryKey, m, audit, data);
    }

    /**
     * 去除多余字段
     * 
     * @param tableName
     * @param m
     * @param audit
     * @param data
     *            包含主键信息
     * @return
     */
    @SuppressWarnings({
        "rawtypes"
    })
    public static boolean updateAndKeep(String tableName, Model m, Audit audit, Map<String, Object> data) {
        String primaryKey = getPrimaryKey(m);
        Record record = putDataAndKeep(tableName, auditUpdate(m, audit, data), data);
        return update(tableName, primaryKey, record);
    }

    /**
     * 去除多余字段
     * 
     * @param tableName
     * @param primaryKey
     *            Example:"user_id, role_id"
     * @param m
     * @param audit
     * @param data
     * @return
     */
    @SuppressWarnings({
        "rawtypes"
    })
    public static boolean updateAndKeep(String tableName, String primaryKey, Model m, Audit audit,
            Map<String, Object> data) {
        Record record = putDataAndKeep(tableName, auditUpdate(m, audit, data), data);
        return update(tableName, primaryKey, record);
    }

    /**
     * 去除多余字段 用于无审计信息表数据更新
     * 
     * @param m
     * @param data
     *            包含主键信息
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean updateAndKeep(Model m, Map<String, Object> data) {
        String tableName = getTableName(m);
        String primaryKey = getPrimaryKey(m);
        return updateAndKeep(tableName, primaryKey, m, data);
    }

    /**
     * 去除多余字段 用于无审计信息表数据更新
     * 
     * @param tableName
     * @param m
     * @param data
     *            包含主键信息
     * @return
     */
    @SuppressWarnings({
        "rawtypes"
    })
    public static boolean updateAndKeep(String tableName, Model m, Map<String, Object> data) {
        String primaryKey = getPrimaryKey(m);
        Record record = putDataAndKeep(tableName, m, data);
        return update(tableName, primaryKey, record);
    }

    /**
     * 去除多余字段
     * 
     * @param tableName
     * @param primaryKey
     *            Example:"user_id, role_id"
     * @param m
     * @param data
     *            包含主键信息
     * @return
     */
    @SuppressWarnings({
        "rawtypes"
    })
    public static boolean updateAndKeep(String tableName, String primaryKey, Model m, Map<String, Object> data) {
        Record record = putDataAndKeep(tableName, m, data);
        return update(tableName, primaryKey, record);
    }

    /**
     * 去除多余字段，再转为Record
     * 
     * @param tableName
     * @param m
     * @param data
     * @return
     */
    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    public static Record putDataAndKeep(String tableName, Model m, Map<String, Object> data) {
        return m.put(data).keep(tableAttrs.get(tableName)).toRecord();
    }

    /**
     * @param m
     * @param list
     * @param batchSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static int[] batchInsert(Model m, List<Map<String, Object>> list, int batchSize) {
        String tableName = getTableName(m);
        return batchSave(m, tableName, list, batchSize);
    }

    /**
     * @param m
     * @param tableName
     * @param list
     * @param batchSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static int[] batchInsert(Model m, String tableName, List<Map<String, Object>> list, int batchSize) {
        String pk = getPrimaryKey(m);
        return batchSave(tableName, toRecord(m, tableName, pk, list), batchSize);
    }

    /**
     * @param m
     * @param list
     * @param batchSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static int[] batchSave(Model m, List<Map<String, Object>> list, int batchSize) {
        String tableName = getTableName(m);
        return batchSave(m, tableName, list, batchSize);
    }

    /**
     * @param m
     * @param tableName
     * @param list
     * @param batchSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static int[] batchSave(Model m, String tableName, List<Map<String, Object>> list, int batchSize) {
        String pk = getPrimaryKey(m);
        return batchSave(tableName, toRecord(m, tableName, pk, list), batchSize);
    }

    /**
     * @param m
     * @param tableName
     * @param pk
     * @param list
     * @return
     */
    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    public static List<Record> toRecord(Model m, String tableName, String pk, List<Map<String, Object>> list) {
        List<Record> result = new ArrayList<Record>();
        for (Map<String, Object> data: list) {
            if (!data.containsKey(pk)) {
                data.put(pk, getId(tableName));
            }
            result.add(m.put(data).toRecord());
        }
        return result;
    }

    /**
     * @param m
     * @param audit
     * @param list
     * @param batchSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static int[] batchSave(Model m, Audit audit, List<Map<String, Object>> list, int batchSize) {
        String tableName = getTableName(m);
        return batchSave(m, audit, tableName, list, batchSize);
    }

    /**
     * @param m
     * @param audit
     * @param tableName
     * @param list
     * @param batchSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static int[] batchSave(Model m, Audit audit, String tableName, List<Map<String, Object>> list,
            int batchSize) {
        String pk = getPrimaryKey(m);
        return batchSave(tableName, toRecord(m, audit, tableName, pk, list), batchSize);
    }

    /**
     * @param m
     * @param audit
     * @param tableName
     * @param pk
     * @param list
     * @return
     */
    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    public static List<Record> toRecord(Model m, Audit audit, String tableName, String pk,
            List<Map<String, Object>> list) {
        List<Record> result = new ArrayList<Record>();
        for (Map<String, Object> data: list) {
            if (!data.containsKey(pk)) {
                data.put(pk, getId(tableName));
            }
            result.add(m.put(initInsert(m, audit, data)).toRecord());
        }
        return result;
    }

    /**
     * @param m
     * @param list
     *            map中包含主键信息
     * @param batchSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static int[] batchUpdate(Model m, List<Map<String, Object>> list, int batchSize) {
        return batchUpdate(getTableName(m), getPrimaryKey(m), toRecord(m, list), batchSize);
    }

    /**
     * @param m
     * @param tableName
     * @param list
     *            map中包含主键信息
     * @param batchSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static int[] batchUpdate(Model m, String tableName, List<Map<String, Object>> list, int batchSize) {
        return batchUpdate(tableName, getPrimaryKey(m), toRecord(m, list), batchSize);
    }

    /**
     * @param m
     * @param list
     *            map中包含主键信息
     * @return
     */
    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    public static List<Record> toRecord(Model m, List<Map<String, Object>> list) {
        List<Record> result = new ArrayList<Record>();
        for (Map<String, Object> data: list) {
            result.add(m.put(data).toRecord());
        }
        return result;
    }

    /**
     * @param m
     * @param list
     *            map中包含主键信息
     * @param batchSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static int[] batchUpdateAndKeep(Model m, List<Map<String, Object>> list, int batchSize) {
        return batchUpdate(getTableName(m), getPrimaryKey(m), toRecordAndKeep(m, list), batchSize);
    }

    /**
     * @param m
     * @param tableName
     * @param list
     *            map中包含主键信息
     * @param batchSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static int[] batchUpdateAndKeep(Model m, String tableName, List<Map<String, Object>> list, int batchSize) {
        return batchUpdate(tableName, getPrimaryKey(m), toRecordAndKeep(m, list), batchSize);
    }

    /**
     * @param m
     * @param list
     * @return
     */
    @SuppressWarnings({
        "rawtypes"
    })
    public static List<Record> toRecordAndKeep(Model m, List<Map<String, Object>> list) {
        String tableName = getTableName(m);
        List<Record> result = new ArrayList<Record>();
        for (Map<String, Object> data: list) {
            result.add(putDataAndKeep(tableName, m, data));
        }
        return result;
    }

    /**
     * @param m
     * @param audit
     * @param list
     *            map中包含主键信息
     * @param batchSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static int[] batchUpdate(Model m, Audit audit, List<Map<String, Object>> list, int batchSize) {
        return batchUpdate(getTableName(m), getPrimaryKey(m), toRecord(m, audit, list), batchSize);
    }

    /**
     * @param m
     * @param audit
     * @param tableName
     * @param list
     *            map中包含主键信息
     * @param batchSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static int[] batchUpdate(Model m, Audit audit, String tableName, List<Map<String, Object>> list,
            int batchSize) {
        return batchUpdate(tableName, getPrimaryKey(m), toRecord(m, audit, list), batchSize);
    }

    /**
     * @param m
     * @param audit
     * @param list
     *            map中包含主键信息
     * @return
     */
    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    public static List<Record> toRecord(Model m, Audit audit, List<Map<String, Object>> list) {
        List<Record> result = new ArrayList<Record>();
        for (Map<String, Object> data: list) {
            result.add(auditUpdate(m, audit, data).put(data).toRecord());
        }
        return result;
    }

    /**
     * @param m
     * @param audit
     * @param list
     *            map中包含主键信息
     * @param batchSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static int[] batchUpdateAndKeep(Model m, Audit audit, List<Map<String, Object>> list, int batchSize) {
        return batchUpdate(getTableName(m), toRecordAndKeep(m, audit, list), batchSize);
    }

    /**
     * @param m
     * @param audit
     * @param tableName
     * @param list
     *            map中包含主键信息
     * @param batchSize
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static int[] batchUpdateAndKeep(Model m, Audit audit, String tableName, List<Map<String, Object>> list,
            int batchSize) {
        return batchUpdate(tableName, toRecordAndKeep(m, audit, list), batchSize);
    }

    /**
     * @param m
     * @param audit
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static List<Record> toRecordAndKeep(Model m, Audit audit, List<Map<String, Object>> list) {
        String tableName = getTableName(m);
        List<Record> result = new ArrayList<Record>();
        for (Map<String, Object> data: list) {
            result.add(putDataAndKeep(tableName, auditUpdate(m, audit, data), data));
        }
        return result;
    }

    /**
     * @param m
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Model keeyModel(Model m) {
        String tableName = getTableName(m);
        return m.keep(tableAttrs.get(tableName));
    }

    /**
     * @param m
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Model keeyModel(Model m, String tableName) {
        return m.keep(tableAttrs.get(tableName));
    }

    /**
     * @param m
     * @param audit
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Model auditInsert(Model m, Audit audit) {
        return initInsert(initId(m), audit);
    }

    /**
     * @param m
     * @param tableName
     * @param primaryKey
     * @param audit
     * @return
     */
    @Deprecated
    @SuppressWarnings("rawtypes")
    public static Model auditInsert(Model m, String tableName, String primaryKey, Audit audit) {
        if (m.get(primaryKey) == null) {
            m.set(primaryKey, idWokers.get(tableName).nextId());
        }
        return initInsert(m, audit);
    }

    /**
     * model中set审计信息
     * 
     * @param m
     * @param audit
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Model auditUpdate(Model m, Audit audit) {
        m.set("u_by", audit.getId());
        m.set("u_name", audit.getName());
        m.set("u_date", new Date());
        return m;
    }

    /**
     * data中set审计信息
     * 
     * @param m
     * @param audit
     * @param data
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Model auditUpdate(Model m, Audit audit, Map<String, Object> data) {
        data.put("u_by", audit.getId());
        data.put("u_name", audit.getName());
        data.put("u_date", new Date());
        return m;
    }

    /**
     * @param m
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Model initId(Model m) {
        Tab t = m.getClass().getAnnotation(Tab.class);
        String pkName = t.pk();
        if (StrKit.notBlank(pkName)) {
            if (m.get(pkName) == null) {
                m.set(pkName, idWokers.get(t.value()).nextId());
            }
        } else {
            if (m.get(DEFAULT_PK) == null) {
                m.set(DEFAULT_PK, idWokers.get(t.value()).nextId());
            }
        }
        return m;
    }

    /**
     * @param m
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Model initId(Model m, Map<String, Object> data) {
        Tab t = m.getClass().getAnnotation(Tab.class);
        String pkName = t.pk();
        if (StrKit.notBlank(pkName)) {
            if (!data.containsKey(pkName)) {
                data.put(pkName, idWokers.get(t.value()).nextId());
            }
        } else {
            String pk = getPrimaryKey(m);
            if (!data.containsKey(pk)) {
                data.put(pk, idWokers.get(t.value()).nextId());
            }
        }
        return m;
    }

    @SuppressWarnings("rawtypes")
    public static Model initId(Model m, String tableName, String primaryKey, Map<String, Object> data) {
        if (StrKit.notBlank(primaryKey)) {
            if (!data.containsKey(primaryKey)) {
                data.put(primaryKey, idWokers.get(tableName).nextId());
            }
        } else {
            String pk = getPrimaryKey(m);
            if (!data.containsKey(pk)) {
                data.put(pk, idWokers.get(tableName).nextId());
            }
        }
        return m;
    }

    /**
     * @param tableName
     * @param pk
     * @param data
     * @return
     */
    public static Map<String, Object> initId(String tableName, String pk, Map<String, Object> data) {
        if (!data.containsKey(pk)) {
            data.put(pk, getId(tableName));
        }
        return data;
    }

    @SuppressWarnings("rawtypes")
    private static Model initInsert(Model m, Audit audit) {
        Date date = new Date();
        m.set("c_by", audit.getId());
        m.set("c_name", audit.getName());
        m.set("c_date", date);
        m.set("u_by", audit.getId());
        m.set("u_name", audit.getName());
        m.set("u_date", date);
        return m;
    }

    /**
     * 初始化审计数据
     * 
     * @param tableName
     * @param primaryKey
     *            Example:"user_id, role_id"
     * @param audit
     * @param data
     * @return
     */
    public static Map<String, Object> initInsert(String tableName, String primaryKey, Audit audit,
            Map<String, Object> data) {
        if (!data.containsKey(primaryKey)) {
            data.put(primaryKey, idWokers.get(tableName).nextId());
        }
        initInsertAuditData(audit, data);
        return data;
    }

    /**
     * 初始化审计数据
     * 
     * @param m
     * @param audit
     * @param data
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> initInsert(Model m, Audit audit, Map<String, Object> data) {
        Tab t = m.getClass().getAnnotation(Tab.class);
        String pkName = t.pk();
        if (StrKit.notBlank(pkName)) {
            if (!data.containsKey(pkName)) {
                data.put(pkName, idWokers.get(t.value()).nextId());
            }
        } else {
            String pk = getPrimaryKey(m);
            if (!data.containsKey(pk)) {
                data.put(pk, idWokers.get(t.value()).nextId());
            }
        }
        initInsertAuditData(audit, data);
        return data;
    }

    private static Map<String, Object> initInsertAuditData(Audit audit, Map<String, Object> data) {
        Date date = new Date();
        data.put("c_by", audit.getId());
        data.put("c_name", audit.getName());
        data.put("c_date", date);
        data.put("u_by", audit.getId());
        data.put("u_name", audit.getName());
        data.put("u_date", date);
        return data;
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

    /**
     * @param tableName
     * @return
     */
    public static IdWorker getIdWoker(String tableName) {
        return idWokers.get(tableName);
    }

    /**
     * @param tableName
     * @return
     */
    public static long getId(String tableName) {
        return idWokers.get(tableName).nextId();
    }

    /**
     * @param modelClazz
     * @return
     */
    @SuppressWarnings({
        "rawtypes", "unchecked"
    })
    public static Map<String, Class<?>> getColumnTypeMap(Class modelClazz) {
        Table table = TableMapping.me().getTable(modelClazz);
        return table.getColumnTypeMap();
    }

    /**
     * @param m
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Map<String, Class<?>> getColumnTypeMap(Model m) {
        Table table = TableMapping.me().getTable(m.getClass());
        return table.getColumnTypeMap();
    }

    /**
     * @param m
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String[] getAttrs(Model m) {
        Map<String, Class<?>> map = getColumnTypeMap(m);
        String[] attrs = new String[map.size()];
        int i = 0;
        for (String s: map.keySet()) {
            attrs[i] = s;
            i++;
        }
        return attrs;
    }

    /**
     * @param modelClazz
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String[] getAttrs(Class modelClazz) {
        Map<String, Class<?>> map = getColumnTypeMap(modelClazz);
        String[] attrs = new String[map.size()];
        int i = 0;
        for (String s: map.keySet()) {
            attrs[i] = s;
            i++;
        }
        return attrs;
    }

    /**
     * @param tableName
     * @return
     */
    public static IdWorker setIdWorker(String tableName, IdWorker idWorker) {
        return idWokers.put(tableName, idWorker);
    }

    /**
     * @param tableAttrs
     */
    public static void setTableAttrs(String tableName, String[] columns) {
        tableAttrs.put(tableName, columns);
    }

    // 父类的方法

    protected static <T> List<T> query(Config config, Connection conn, String sql, Object... paras)
            throws SQLException {
        return DbBase.query(config, conn, sql, paras);
    }

    /**
     * @see #query(String, String, Object...)
     */
    public static <T> List<T> query(String sql, Object... paras) {
        return DbBase.query(sql, paras);
    }

    /**
     * @see #query(String, Object...)
     * @param sql
     *            an SQL statement
     */
    public static <T> List<T> query(String sql) {
        return DbBase.query(sql);
    }

    /**
     * Execute sql query and return the first result. I recommend add "limit 1"
     * in your sql.
     * 
     * @param sql
     *            an SQL statement that may contain one or more '?' IN parameter
     *            placeholders
     * @param paras
     *            the parameters of sql
     * @return Object[] if your sql has select more than one column, and it
     *         return Object if your sql has select only one column.
     */
    public static <T> T queryFirst(String sql, Object... paras) {
        return DbBase.queryFirst(sql, paras);
    }

    /**
     * @see #queryFirst(String, Object...)
     * @param sql
     *            an SQL statement
     */
    public static <T> T queryFirst(String sql) {
        return DbBase.queryFirst(sql);
    }

    // 26 queryXxx method below -----------------------------------------------
    /**
     * Execute sql query just return one column.
     * 
     * @param <T>
     *            the type of the column that in your sql's select statement
     * @param sql
     *            an SQL statement that may contain one or more '?' IN parameter
     *            placeholders
     * @param paras
     *            the parameters of sql
     * @return List<T>
     */
    public static <T> T queryColumn(String sql, Object... paras) {
        return DbBase.queryColumn(sql, paras);
    }

    public static <T> T queryColumn(String sql) {
        return DbBase.queryColumn(sql);
    }

    public static String queryStr(String sql, Object... paras) {
        return DbBase.queryStr(sql, paras);
    }

    public static String queryStr(String sql) {
        return DbBase.queryStr(sql);
    }

    public static Integer queryInt(String sql, Object... paras) {
        return DbBase.queryInt(sql, paras);
    }

    public static Integer queryInt(String sql) {
        return DbBase.queryInt(sql);
    }

    public static Long queryLong(String sql, Object... paras) {
        return DbBase.queryLong(sql, paras);
    }

    public static Long queryLong(String sql) {
        return DbBase.queryLong(sql);
    }

    public static Double queryDouble(String sql, Object... paras) {
        return DbBase.queryDouble(sql, paras);
    }

    public static Double queryDouble(String sql) {
        return DbBase.queryDouble(sql);
    }

    public static Float queryFloat(String sql, Object... paras) {
        return DbBase.queryFloat(sql, paras);
    }

    public static Float queryFloat(String sql) {
        return DbBase.queryFloat(sql);
    }

    public static java.math.BigDecimal queryBigDecimal(String sql, Object... paras) {
        return DbBase.queryBigDecimal(sql, paras);
    }

    public static java.math.BigDecimal queryBigDecimal(String sql) {
        return DbBase.queryBigDecimal(sql);
    }

    public static byte[] queryBytes(String sql, Object... paras) {
        return DbBase.queryBytes(sql, paras);
    }

    public static byte[] queryBytes(String sql) {
        return DbBase.queryBytes(sql);
    }

    public static java.util.Date queryDate(String sql, Object... paras) {
        return DbBase.queryDate(sql, paras);
    }

    public static java.util.Date queryDate(String sql) {
        return DbBase.queryDate(sql);
    }

    public static java.sql.Time queryTime(String sql, Object... paras) {
        return DbBase.queryTime(sql, paras);
    }

    public static java.sql.Time queryTime(String sql) {
        return DbBase.queryTime(sql);
    }

    public static java.sql.Timestamp queryTimestamp(String sql, Object... paras) {
        return DbBase.queryTimestamp(sql, paras);
    }

    public static java.sql.Timestamp queryTimestamp(String sql) {
        return DbBase.queryTimestamp(sql);
    }

    public static Boolean queryBoolean(String sql, Object... paras) {
        return DbBase.queryBoolean(sql, paras);
    }

    public static Boolean queryBoolean(String sql) {
        return DbBase.queryBoolean(sql);
    }

    public static Number queryNumber(String sql, Object... paras) {
        return DbBase.queryNumber(sql, paras);
    }

    public static Number queryNumber(String sql) {
        return DbBase.queryNumber(sql);
    }

    /**
     * Execute sql update
     */
    protected static int update(Config config, Connection conn, String sql, Object... paras) throws SQLException {
        return DbBase.update(config, conn, sql, paras);
    }

    /**
     * Execute update, insert or delete sql statement.
     * 
     * @param sql
     *            an SQL statement that may contain one or more '?' IN parameter
     *            placeholders
     * @param paras
     *            the parameters of sql
     * @return either the row count for <code>INSERT</code>, <code>UPDATE</code>
     *         , or <code>DELETE</code> statements, or 0 for SQL statements that
     *         return nothing
     */
    public static int update(String sql, Object... paras) {
        return DbBase.update(sql, paras);
    }

    /**
     * @see #update(String, Object...)
     * @param sql
     *            an SQL statement
     */
    public static int update(String sql) {
        return DbBase.update(sql);
    }

    protected static List<Record> find(Config config, Connection conn, String sql, Object... paras)
            throws SQLException {
        return DbBase.find(config, conn, sql, paras);
    }

    /**
     * @see #find(String, String, Object...)
     */
    public static List<Record> find(String sql, Object... paras) {
        return DbBase.find(sql, paras);
    }

    /**
     * @see #find(String, String, Object...)
     * @param sql
     *            the sql statement
     */
    public static List<Record> find(String sql) {
        return DbBase.find(sql);
    }

    /**
     * Find first record. I recommend add "limit 1" in your sql.
     * 
     * @param sql
     *            an SQL statement that may contain one or more '?' IN parameter
     *            placeholders
     * @param paras
     *            the parameters of sql
     * @return the Record object
     */
    public static Record findFirst(String sql, Object... paras) {
        return DbBase.findFirst(sql, paras);
    }

    /**
     * @see #findFirst(String, Object...)
     * @param sql
     *            an SQL statement
     */
    public static Record findFirst(String sql) {
        return DbBase.findFirst(sql);
    }

    /**
     * Find record by id with default primary key.
     * 
     * <pre>
     * Example:
     * Record user = Db.findById("user", 15);
     * </pre>
     * 
     * @param tableName
     *            the table name of the table
     * @param idValue
     *            the id value of the record
     */
    public static Record findById(String tableName, Object idValue) {
        return DbBase.findById(tableName, idValue);
    }

    /**
     * Find record by id.
     * 
     * <pre>
     * Example:
     * Record user = Db.findById("user", "user_id", 123);
     * Record userRole = Db.findById("user_role", "user_id, role_id", 123, 456);
     * </pre>
     * 
     * @param tableName
     *            the table name of the table
     * @param primaryKey
     *            the primary key of the table, composite primary key is
     *            separated by comma character: ","
     * @param idValue
     *            the id value of the record, it can be composite id values
     */
    public static Record findById(String tableName, String primaryKey, Object... idValue) {
        return DbBase.findById(tableName, primaryKey, idValue);
    }

    /**
     * Delete record by id with default primary key.
     * 
     * <pre>
     * Example: Db.deleteById("user", 15);
     * </pre>
     * 
     * @param tableName
     *            the table name of the table
     * @param idValue
     *            the id value of the record
     * @return true if delete succeed otherwise false
     */
    public static boolean deleteById(String tableName, Object idValue) {
        return DbBase.deleteById(tableName, idValue);
    }

    /**
     * Delete record by id.
     * 
     * <pre>
     * Example: Db.deleteById("user", "user_id", 15);
     * Db.deleteById("user_role", "user_id, role_id", 123, 456);
     * </pre>
     * 
     * @param tableName
     *            the table name of the table
     * @param primaryKey
     *            the primary key of the table, composite primary key is
     *            separated by comma character: ","
     * @param idValue
     *            the id value of the record, it can be composite id values
     * @return true if delete succeed otherwise false
     */
    public static boolean deleteById(String tableName, String primaryKey, Object... idValue) {
        return DbBase.deleteById(tableName, primaryKey, idValue);
    }

    /**
     * Delete record.
     * 
     * <pre>
     * Example:
     * boolean succeed = Db.delete("user", "id", user);
     * </pre>
     * 
     * @param tableName
     *            the table name of the table
     * @param primaryKey
     *            the primary key of the table, composite primary key is
     *            separated by comma character: ","
     * @param record
     *            the record
     * @return true if delete succeed otherwise false
     */
    public static boolean delete(String tableName, String primaryKey, Record record) {
        return DbBase.delete(tableName, primaryKey, record);
    }

    /**
     * <pre>
     * Example:
     * boolean succeed = Db.delete("user", user);
     * </pre>
     * 
     * @see #delete(String, String, Record)
     */
    public static boolean delete(String tableName, Record record) {
        return DbBase.delete(tableName, record);
    }

    // 修改为public
    protected static Page<Record> paginate(Config config, Connection conn, int pageNumber, int pageSize, String select,
            String sqlExceptSelect, Object... paras) throws SQLException {
        return DbBase.paginate(config, conn, pageNumber, pageSize, select, sqlExceptSelect, paras);
    }

    /**
     * Paginate.
     * 
     * @param pageNumber
     *            the page number
     * @param pageSize
     *            the page size
     * @param select
     *            the select part of the sql statement
     * @param sqlExceptSelect
     *            the sql statement excluded select part
     * @param paras
     *            the parameters of sql
     * @return the Page object
     */
    public static Page<Record> paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect,
            Object... paras) {
        return DbBase.paginate(pageNumber, pageSize, select, sqlExceptSelect, paras);
    }

    public static Page<Record> paginate(int pageNumber, int pageSize, boolean isGroupBySql, String select,
            String sqlExceptSelect, Object... paras) {
        return DbBase.paginate(pageNumber, pageSize, isGroupBySql, select, sqlExceptSelect, paras);
    }

    /**
     * @see #paginate(String, int, int, String, String, Object...)
     */
    public static Page<Record> paginate(int pageNumber, int pageSize, String select, String sqlExceptSelect) {
        return DbBase.paginate(pageNumber, pageSize, select, sqlExceptSelect);
    }

    protected static boolean save(Config config, Connection conn, String tableName, String primaryKey, Record record)
            throws SQLException {
        return DbBase.save(config, conn, tableName, primaryKey, record);
    }

    /**
     * Save record.
     * 
     * <pre>
     * Example:
     * Record userRole = new Record().set("user_id", 123).set("role_id", 456);
     * Db.save("user_role", "user_id, role_id", userRole);
     * </pre>
     * 
     * @param tableName
     *            the table name of the table
     * @param primaryKey
     *            the primary key of the table, composite primary key is
     *            separated by comma character: ","
     * @param record
     *            the record will be saved
     * @param true
     *            if save succeed otherwise false
     */
    public static boolean save(String tableName, String primaryKey, Record record) {
        return DbBase.save(tableName, primaryKey, record);
    }

    /**
     * @see #save(String, String, Record)
     */
    public static boolean save(String tableName, Record record) {
        return DbBase.save(tableName, record);
    }

    // 修改为公共方法
    protected static boolean update(Config config, Connection conn, String tableName, String primaryKey, Record record)
            throws SQLException {
        return DbBase.update(config, conn, tableName, primaryKey, record);
    }

    /**
     * Update Record.
     * 
     * <pre>
     * Example: Db.update("user_role", "user_id, role_id", record);
     * </pre>
     * 
     * @param tableName
     *            the table name of the Record save to
     * @param primaryKey
     *            the primary key of the table, composite primary key is
     *            separated by comma character: ","
     * @param record
     *            the Record object
     * @param true
     *            if update succeed otherwise false
     */
    public static boolean update(String tableName, String primaryKey, Record record) {
        return DbBase.update(tableName, primaryKey, record);
    }

    /**
     * Update record with default primary key.
     * 
     * <pre>
     * Example: Db.update("user", record);
     * </pre>
     * 
     * @see #update(String, String, Record)
     */
    public static boolean update(String tableName, Record record) {
        return DbBase.update(tableName, record);
    }

    /**
     * @see #execute(String, ICallback)
     */
    public static Object execute(ICallback callback) {
        return DbBase.execute(callback);
    }

    /**
     * Execute callback. It is useful when all the API can not satisfy your
     * requirement.
     * 
     * @param config
     *            the Config object
     * @param callback
     *            the ICallback interface
     */
    protected static Object execute(Config config, ICallback callback) {
        return DbBase.execute(config, callback);
    }

    /**
     * Execute transaction.
     * 
     * @param config
     *            the Config object
     * @param transactionLevel
     *            the transaction level
     * @param atom
     *            the atom operation
     * @return true if transaction executing succeed otherwise false
     */
    protected static boolean tx(Config config, int transactionLevel, IAtom atom) {
        return DbBase.tx(config, transactionLevel, atom);
    }

    public static boolean tx(int transactionLevel, IAtom atom) {
        return DbBase.tx(transactionLevel, atom);
    }

    /**
     * Execute transaction with default transaction level.
     * 
     * @see #tx(int, IAtom)
     */
    public static boolean tx(IAtom atom) {
        return DbBase.tx(atom);
    }

    /**
     * Find Record by cache.
     * 
     * @see #find(String, Object...)
     * @param cacheName
     *            the cache name
     * @param key
     *            the key used to get date from cache
     * @return the list of Record
     */
    public static List<Record> findByCache(String cacheName, Object key, String sql, Object... paras) {
        return DbBase.findByCache(cacheName, key, sql, paras);
    }

    /**
     * @see #findByCache(String, Object, String, Object...)
     */
    public static List<Record> findByCache(String cacheName, Object key, String sql) {
        return DbBase.findByCache(cacheName, key, sql);
    }

    /**
     * Find first record by cache. I recommend add "limit 1" in your sql.
     * 
     * @see #findFirst(String, Object...)
     * @param cacheName
     *            the cache name
     * @param key
     *            the key used to get date from cache
     * @param sql
     *            an SQL statement that may contain one or more '?' IN parameter
     *            placeholders
     * @param paras
     *            the parameters of sql
     * @return the Record object
     */
    public static Record findFirstByCache(String cacheName, Object key, String sql, Object... paras) {
        return DbBase.findFirstByCache(cacheName, key, sql, paras);
    }

    /**
     * @see #findFirstByCache(String, Object, String, Object...)
     */
    public static Record findFirstByCache(String cacheName, Object key, String sql) {
        return DbBase.findFirstByCache(cacheName, key, sql);
    }

    /**
     * Paginate by cache.
     * 
     * @see #paginate(int, int, String, String, Object...)
     * @return Page
     */
    public static Page<Record> paginateByCache(String cacheName, Object key, int pageNumber, int pageSize,
            String select, String sqlExceptSelect, Object... paras) {
        return DbBase.paginateByCache(cacheName, key, pageNumber, pageSize, select, sqlExceptSelect, paras);
    }

    public static Page<Record> paginateByCache(String cacheName, Object key, int pageNumber, int pageSize,
            boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) {
        return DbBase.paginateByCache(cacheName, key, pageNumber, pageSize, isGroupBySql, select, sqlExceptSelect,
                paras);
    }

    /**
     * @see #paginateByCache(String, Object, int, int, String, String,
     *      Object...)
     */
    public static Page<Record> paginateByCache(String cacheName, Object key, int pageNumber, int pageSize,
            String select, String sqlExceptSelect) {
        return DbBase.paginateByCache(cacheName, key, pageNumber, pageSize, select, sqlExceptSelect);
    }

    /**
     * @see DbBase#batch(String, Object[][], int)
     */
    public static int[] batch(String sql, Object[][] paras, int batchSize) {
        return DbBase.batch(sql, paras, batchSize);
    }

    /**
     * @see DbBase#batch(String, String, List, int)
     */
    @SuppressWarnings("rawtypes")
    public static int[] batch(String sql, String columns, List modelOrRecordList, int batchSize) {
        return DbBase.batch(sql, columns, modelOrRecordList, batchSize);
    }

    /**
     * @see DbBase#batch(List, int)
     */
    public static int[] batch(List<String> sqlList, int batchSize) {
        return DbBase.batch(sqlList, batchSize);
    }

    /**
     * @see DbBase#batchSave(List, int)
     */

    @SuppressWarnings("rawtypes")
    public static int[] batchSave(List<? extends Model> modelList, int batchSize) {
        return DbBase.batchSave(modelList, batchSize);
    }

    /**
     * @see DbBase#batchSave(String, List, int)
     */
    public static int[] batchSave(String tableName, List<Record> recordList, int batchSize) {
        return DbBase.batchSave(tableName, recordList, batchSize);
    }

    /**
     * @see DbBase#batchUpdate(List, int)
     */
    @SuppressWarnings("rawtypes")
    public static int[] batchUpdate(List<? extends Model> modelList, int batchSize) {
        return DbBase.batchUpdate(modelList, batchSize);
    }

    /**
     * @see DbBase#batchUpdate(String, String, List, int)
     */
    public static int[] batchUpdate(String tableName, String primaryKey, List<Record> recordList, int batchSize) {
        return DbBase.batchUpdate(tableName, primaryKey, recordList, batchSize);
    }

    /**
     * @see DbBase#batchUpdate(String, List, int)
     */
    public static int[] batchUpdate(String tableName, List<Record> recordList, int batchSize) {
        return DbBase.batchUpdate(tableName, recordList, batchSize);
    }

    /**
     * @param sql
     * @param desc
     * @param order
     * @return
     */
    private static StringBuilder appendOrder(StringBuilder sql, boolean desc, String... order) {
        if (order != null && order.length > 0) {
            sql.append(" order by ");
            int max = order.length - 1;
            for (int i = 0; i < max; i++) {
                sql.append(order[i] + ",");
            }
            sql.append(order[max]);
            if (desc) {
                sql.append(" desc");
            }
        }
        return sql;
    }

    private static StringBuilder appendOrder(StringBuilder sql, boolean desc, String order) {
        if (order != null) {
            sql.append(" order by " + order);
            if (desc) {
                sql.append(" desc");
            }
        }
        return sql;
    }

    /**
     * 判断map中是否包含该值,再保存进model中
     * 
     * @param model
     * @param key
     * @param data
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static Model setValue(Model model, String key, Map<String, Object> data) {
        if (data.containsKey(key)) {
            model.set(key, data.get(key));
        }
        return model;
    }

}
