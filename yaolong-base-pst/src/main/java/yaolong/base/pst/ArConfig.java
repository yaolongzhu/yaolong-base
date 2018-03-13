package yaolong.base.pst;

import yaolong.base.ar.DbPro;
import yaolong.base.ar.dialect.Dialect;

/**
 *
 * @author Luo Guoxiong
 *
 */
public interface ArConfig {

	public Dialect getDialect();

	public DbPro getDbPro();

}
