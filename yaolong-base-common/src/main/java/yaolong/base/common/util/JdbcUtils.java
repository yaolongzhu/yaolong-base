package yaolong.base.common.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author
 *
 */
public class JdbcUtils {

	public static Set<String> getColumnSet(ResultSet rs) {
		Set<String> set = new HashSet<String>();
		try {
			ResultSetMetaData rsd = rs.getMetaData();
			int size = rsd.getColumnCount() + 1;
			for (int i = 1; i < size; i++) {
				String name = rsd.getColumnName(i);
				set.add(name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}

}
