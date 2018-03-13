package yaolong.base.pst.config;

import yaolong.base.ar.DbPro;
import yaolong.base.ar.dialect.Dialect;
import yaolong.base.pst.ArConfig;

/**
 *
 * @author
 *
 */
public class ArConfigImpl implements ArConfig {

	public Dialect dialect;

	public DbPro dbPro;

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public DbPro getDbPro() {
		return dbPro;
	}

	public void setDbPro(DbPro dbPro) {
		this.dbPro = dbPro;
	}

}
