package yaolong.base.pst.jdbc;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Luo Guoxiong
 *
 */
public interface Jdbc {
	/**
	 * 
	 * @param sql
	 * @param parame
	 * @return
	 */
	public List<Map<String, Object>> query(String sql, Object... parame);

	/**
	 * 
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> query(String sql);

	/**
	 * 
	 * @param sql
	 * @param parame
	 * @return
	 */
	public Map<String, Object> findFirst(String sql, Object... parame);

	/**
	 * 
	 * @param sql
	 * @return
	 */
	public Map<String, Object> findFirst(String sql);

	/**
	 * 
	 * @param table
	 * @param id
	 * @param pk
	 * @param parame
	 * @return
	 */
	public Map<String, Object> findById(String table, Long id, String pk, Object... parame);

	/**
	 * 
	 * @param table
	 * @param id
	 * @param pk
	 * @return
	 */
	public Map<String, Object> findById(String table, Long id, String pk);

	/**
	 * 
	 * @param table
	 * @param id
	 * @param parame
	 * @return
	 */
	public Map<String, Object> findById(String table, Long id, Object... parame);

	/**
	 * 
	 * @param table
	 * @param id
	 * @return
	 */
	public Map<String, Object> findById(String table, Long id);

}
