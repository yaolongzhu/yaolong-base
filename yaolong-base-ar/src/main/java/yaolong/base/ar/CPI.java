package yaolong.base.ar;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
*
* @author
*
*/
public abstract class CPI {
	
	/**
	 * Return the attributes map of the model
	 * @param model the model extends from class Model
	 * @return the attributes map of the model
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static final Map<String, Object> getAttrs(Model model) {
		return model.getAttrs();
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static final Set<String> getModifyFlag(Model model) {
		return model.getModifyFlag();
	}
	
	public static <T> List<T> query(Connection conn, String sql, Object... paras) throws SQLException {
		return DbBase.query(DbKit.config, conn, sql, paras);
	}
	
	public static <T> List<T> query(String configName, Connection conn, String sql, Object... paras) throws SQLException {
		return DbBase.query(DbKit.getConfig(configName), conn, sql, paras);
	}
	
	/**
	 * Return the columns map of the record
	 * @param record the Record object
	 * @return the columns map of the record
	public static final Map<String, Object> getColumns(Record record) {
		return record.getColumns();
	} */
	
	public static List<Record> find(Connection conn, String sql, Object... paras) throws SQLException {
		return DbBase.find(DbKit.config, conn, sql, paras);
	}
	
	public static List<Record> find(String configName, Connection conn, String sql, Object... paras) throws SQLException {
		return DbBase.find(DbKit.getConfig(configName), conn, sql, paras);
	}
	
	public static Page<Record> paginate(Connection conn, int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) throws SQLException {
		return DbBase.paginate(DbKit.config, conn, pageNumber, pageSize, select, sqlExceptSelect, paras);
	}
	
	public static Page<Record> paginate(String configName, Connection conn, int pageNumber, int pageSize, String select, String sqlExceptSelect, Object... paras) throws SQLException {
		return DbBase.paginate(DbKit.getConfig(configName), conn, pageNumber, pageSize, select, sqlExceptSelect, paras);
	}
	
	public static int update(Connection conn, String sql, Object... paras) throws SQLException {
		return DbBase.update(DbKit.config, conn, sql, paras);
	}
	
	public static int update(String configName, Connection conn, String sql, Object... paras) throws SQLException {
		return DbBase.update(DbKit.getConfig(configName), conn, sql, paras);
	}
	
	public static void setTablePrimaryKey(Table table, String primaryKey) {
		table.setPrimaryKey(primaryKey);
	}
}


