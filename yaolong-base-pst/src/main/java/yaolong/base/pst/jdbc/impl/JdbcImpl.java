package yaolong.base.pst.jdbc.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.ResultSetExtractor;

import yaolong.base.pst.jdbc.BaseDao;
import yaolong.base.pst.jdbc.Jdbc;
import yaolong.base.pst.util.JdbcUtils;

/**
 *
 * @author Luo Guoxiong
 *
 */
public class JdbcImpl extends BaseDao implements Jdbc {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> query(String sql, Object... parame) {
		List<Map<String, Object>> result = (List<Map<String, Object>>) template.query(sql, new Object[] { parame }, new ResultSetExtractor() {
			public List<Map<String, Object>> extractData(ResultSet rs) throws SQLException {
				Set<String> columns = JdbcUtils.getColumnSet(rs);
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				while (rs.next()) {
					Map map = new LinkedHashMap();
					for (String column : columns) {
						map.put(column, rs.getObject(column));
					}
					list.add(map);
				}
				return list;
			};
		});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map<String, Object>> query(String sql) {
		List<Map<String, Object>> result = (List<Map<String, Object>>) template.query(sql, new ResultSetExtractor() {
			public List<Map<String, Object>> extractData(ResultSet rs) throws SQLException {
				Set<String> columns = JdbcUtils.getColumnSet(rs);
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				while (rs.next()) {
					Map map = new LinkedHashMap();
					for (String column : columns) {
						map.put(column, rs.getObject(column));
					}
					list.add(map);
				}
				return list;
			};
		});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> findFirst(String sql, Object... parame) {
		Map<String, Object> result = (Map<String, Object>) template.query(sql, new Object[] { parame }, new ResultSetExtractor() {
			public Map<String, Object> extractData(ResultSet rs) throws SQLException {
				Set<String> columns = JdbcUtils.getColumnSet(rs);
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				while (rs.next()) {
					for (String column : columns) {
						map.put(column, rs.getObject(column));
					}
					break;
				}
				return map;
			};
		});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> findFirst(String sql) {
		Map<String, Object> result = (Map<String, Object>) template.query(sql, new ResultSetExtractor() {
			public Map<String, Object> extractData(ResultSet rs) throws SQLException {
				Set<String> columns = JdbcUtils.getColumnSet(rs);
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				while (rs.next()) {
					for (String column : columns) {
						map.put(column, rs.getObject(column));
					}
					break;
				}
				return map;
			};
		});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> findById(String table, Long id, String pk, Object... parame) {
		if (pk == null || "".equals(pk.trim())) {
			pk = "id";
		}
		String sql = "select * from " + table + " where " + pk + " = " + id;
		Map<String, Object> result = (Map<String, Object>) template.query(sql, new Object[] { parame }, new ResultSetExtractor() {
			public Map<String, Object> extractData(ResultSet rs) throws SQLException {
				Set<String> columns = JdbcUtils.getColumnSet(rs);
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				while (rs.next()) {
					for (String column : columns) {
						map.put(column, rs.getObject(column));
					}
					break;
				}
				return map;
			};
		});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> findById(String table, Long id, String pk) {
		if (pk == null || "".equals(pk.trim())) {
			pk = "id";
		}
		String sql = "select * from " + table + " where " + pk + " = " + id;
		Map<String, Object> result = (Map<String, Object>) template.query(sql, new ResultSetExtractor() {
			public Map<String, Object> extractData(ResultSet rs) throws SQLException {
				Set<String> columns = JdbcUtils.getColumnSet(rs);
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				while (rs.next()) {
					for (String column : columns) {
						map.put(column, rs.getObject(column));
					}
					break;
				}
				return map;
			};
		});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> findById(String table, Long id, Object... parame) {
		String sql = "select * from " + table + " where id = " + id;
		Map<String, Object> result = (Map<String, Object>) template.query(sql, new Object[] { parame }, new ResultSetExtractor() {
			public Map<String, Object> extractData(ResultSet rs) throws SQLException {
				Set<String> columns = JdbcUtils.getColumnSet(rs);
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				while (rs.next()) {
					for (String column : columns) {
						map.put(column, rs.getObject(column));
					}
					break;
				}
				return map;
			};
		});
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> findById(String table, Long id) {
		String sql = "select * from " + table + " where id = " + id;
		Map<String, Object> result = (Map<String, Object>) template.query(sql, new ResultSetExtractor() {
			public Map<String, Object> extractData(ResultSet rs) throws SQLException {
				Set<String> columns = JdbcUtils.getColumnSet(rs);
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				while (rs.next()) {
					for (String column : columns) {
						map.put(column, rs.getObject(column));
					}
					break;
				}
				return map;
			};
		});
		return result;
	}

}
