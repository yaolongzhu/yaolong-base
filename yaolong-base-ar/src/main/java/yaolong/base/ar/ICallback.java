package yaolong.base.ar;

import java.sql.Connection;
import java.sql.SQLException;

/**
*
* @author
*
*/
public interface ICallback {
	
	/**
	 * Place codes here that need call back by active record plugin.
	 * @param conn the JDBC Connection, you need't close this connection after used it, active record plugin will close it automatically
	 */
	Object call(Connection conn) throws SQLException;

}


