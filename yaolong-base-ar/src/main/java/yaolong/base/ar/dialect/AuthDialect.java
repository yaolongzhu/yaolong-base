package yaolong.base.ar.dialect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import yaolong.base.ar.Model;
import yaolong.base.ar.Page;
import yaolong.base.ar.Record;
import yaolong.base.ar.Table;

/**
 *
 * @author
 *
 */
public abstract class AuthDialect extends Dialect {

	public String forTableBuilderDoBuild(String tableName) {
		return "select * from `" + tableName + "` where 1 = 2";
	}

	public void forModelSave(Table table, Map<String, Object> attrs, StringBuilder sql, List<Object> paras) {
		sql.append("insert into `").append(table.getName()).append("`(");
		StringBuilder temp = new StringBuilder(") values(");
		for (Entry<String, Object> e : attrs.entrySet()) {
			String colName = e.getKey();
			if (table.hasColumnLabel(colName)) {
				if (paras.size() > 0) {
					sql.append(", ");
					temp.append(", ");
				}
				sql.append("`").append(colName).append("`");
				temp.append("?");
				paras.add(e.getValue());
			}
		}
		sql.append(temp.toString()).append(")");
	}

	public String forModelDeleteById(Table table) {
		String[] pKeys = table.getPrimaryKey();
		StringBuilder sql = new StringBuilder(45);
		sql.append("delete from `");
		sql.append(table.getName());
		sql.append("` where ");
		for (int i = 0; i < pKeys.length; i++) {
			if (i > 0) {
				sql.append(" and ");
			}
			sql.append("`").append(pKeys[i]).append("` = ?");
		}
		return sql.toString();
	}

	public void forModelUpdate(Table table, Map<String, Object> attrs, Set<String> modifyFlag, StringBuilder sql,
			List<Object> paras) {
		sql.append("update `").append(table.getName()).append("` set ");
		String[] pKeys = table.getPrimaryKey();
		for (Entry<String, Object> e : attrs.entrySet()) {
			String colName = e.getKey();
			if (modifyFlag.contains(colName) && !isPrimaryKey(colName, pKeys) && table.hasColumnLabel(colName)) {
				if (paras.size() > 0) {
					sql.append(", ");
				}
				sql.append("`").append(colName).append("` = ? ");
				paras.add(e.getValue());
			}
		}
		sql.append(" where ");
		for (int i = 0; i < pKeys.length; i++) {
			if (i > 0) {
				sql.append(" and ");
			}
			sql.append("`").append(pKeys[i]).append("` = ?");
			paras.add(attrs.get(pKeys[i]));
		}
	}

	public String forModelFindById(Table table, String columns) {
		StringBuilder sql = new StringBuilder("select ");
		columns = columns.trim();
		if ("*".equals(columns)) {
			sql.append("*");
		} else {
			String[] arr = columns.split(",");
			for (int i = 0; i < arr.length; i++) {
				if (i > 0) {
					sql.append(",");
				}
				sql.append("`").append(arr[i].trim()).append("`");
			}
		}

		sql.append(" from `");
		sql.append(table.getName());
		sql.append("` where ");
		String[] pKeys = table.getPrimaryKey();
		for (int i = 0; i < pKeys.length; i++) {
			if (i > 0) {
				sql.append(" and ");
			}
			sql.append("`").append(pKeys[i]).append("` = ?");
		}
		return sql.toString();
	}

	public String forDbFindById(String tableName, String[] pKeys) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys);

		StringBuilder sql = new StringBuilder("select * from `").append(tableName).append("` where ");
		for (int i = 0; i < pKeys.length; i++) {
			if (i > 0) {
				sql.append(" and ");
			}
			sql.append("`").append(pKeys[i]).append("` = ?");
		}
		return sql.toString();
	}

	public String forDbDeleteById(String tableName, String[] pKeys) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys);

		StringBuilder sql = new StringBuilder("delete from `").append(tableName).append("` where ");
		for (int i = 0; i < pKeys.length; i++) {
			if (i > 0) {
				sql.append(" and ");
			}
			sql.append("`").append(pKeys[i]).append("` = ?");
		}
		return sql.toString();
	}

	/**
	 * Do not delete the String[] pKeys parameter, the element of pKeys needs to
	 * trim()
	 */
	public void forDbSave(String tableName, String[] pKeys, Record record, StringBuilder sql, List<Object> paras) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys); // important

		sql.append("insert into `");
		sql.append(tableName).append("`(");
		StringBuilder temp = new StringBuilder();
		temp.append(") values(");

		for (Entry<String, Object> e : record.getColumns().entrySet()) {
			if (paras.size() > 0) {
				sql.append(", ");
				temp.append(", ");
			}
			sql.append("`").append(e.getKey()).append("`");
			temp.append("?");
			paras.add(e.getValue());
		}
		sql.append(temp.toString()).append(")");
	}

	public void forDbUpdate(String tableName, String[] pKeys, Object[] ids, Record record, StringBuilder sql,
			List<Object> paras) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys);

		sql.append("update `").append(tableName).append("` set ");
		for (Entry<String, Object> e : record.getColumns().entrySet()) {
			String colName = e.getKey();
			if (!isPrimaryKey(colName, pKeys)) {
				if (paras.size() > 0) {
					sql.append(", ");
				}
				sql.append("`").append(colName).append("` = ? ");
				paras.add(e.getValue());
			}
		}
		sql.append(" where ");
		for (int i = 0; i < pKeys.length; i++) {
			if (i > 0) {
				sql.append(" and ");
			}
			sql.append("`").append(pKeys[i]).append("` = ?");
			paras.add(ids[i]);
		}
	}

	public String forPaginate(int pageNumber, int pageSize, String select, String sqlExceptSelect) {
		int offset = pageSize * (pageNumber - 1);
		StringBuilder ret = new StringBuilder();
		ret.append(select).append(" ").append(sqlExceptSelect);
		ret.append(" limit ").append(offset).append(", ").append(pageSize); // limit
		return ret.toString();
	}

	// -----------------------------
	// 父类Dialect中为覆盖的方法
	// -----------------------------

	@Override
	public boolean isOracle() {
		// TODO Auto-generated method stub
		return super.isOracle();
	}

	@Override
	public boolean isTakeOverDbPaginate() {
		// TODO Auto-generated method stub
		return super.isTakeOverDbPaginate();
	}

	@Override
	public Page<Record> takeOverDbPaginate(Connection conn, int pageNumber, int pageSize, Boolean isGroupBySql,
			String select, String sqlExceptSelect, Object... paras) throws SQLException {
		// TODO Auto-generated method stub
		return super.takeOverDbPaginate(conn, pageNumber, pageSize, isGroupBySql, select, sqlExceptSelect, paras);
	}

	@Override
	public boolean isTakeOverModelPaginate() {
		// TODO Auto-generated method stub
		return super.isTakeOverModelPaginate();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page takeOverModelPaginate(Connection conn, Class<? extends Model> modelClass, int pageNumber, int pageSize,
			Boolean isGroupBySql, String select, String sqlExceptSelect, Object... paras) throws Exception {
		// TODO Auto-generated method stub
		return super.takeOverModelPaginate(conn, modelClass, pageNumber, pageSize, isGroupBySql, select,
				sqlExceptSelect, paras);
	}

	@Override
	public void fillStatement(PreparedStatement pst, List<Object> paras) throws SQLException {
		// TODO Auto-generated method stub
		super.fillStatement(pst, paras);
	}

	@Override
	public void fillStatement(PreparedStatement pst, Object... paras) throws SQLException {
		// TODO Auto-generated method stub
		super.fillStatement(pst, paras);
	}

	@Override
	public String getDefaultPrimaryKey() {
		// TODO Auto-generated method stub
		return super.getDefaultPrimaryKey();
	}

	@Override
	public boolean isPrimaryKey(String colName, String[] pKeys) {
		// TODO Auto-generated method stub
		return super.isPrimaryKey(colName, pKeys);
	}

	@Override
	public void trimPrimaryKeys(String[] pKeys) {
		// TODO Auto-generated method stub
		super.trimPrimaryKeys(pKeys);
	}

	@Override
	public String replaceOrderBy(String sql) {
		// TODO Auto-generated method stub
		return super.replaceOrderBy(sql);
	}

}