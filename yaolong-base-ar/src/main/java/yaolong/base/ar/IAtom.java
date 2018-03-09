package yaolong.base.ar;

import java.sql.SQLException;

/**
*
* @author
*
*/
public interface IAtom {
	/**
	 * Place codes here that need transaction support.
	 * @return true if you want to commit the transaction otherwise roll back transaction
	 */
	boolean run() throws SQLException;
}


