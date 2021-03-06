package yaolong.base.ar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import yaolong.base.ar.cache.EhCache;
import yaolong.base.ar.cache.ICache;
import yaolong.base.ar.dialect.Dialect;
import yaolong.base.ar.dialect.MysqlDialect;
import yaolong.base.ar.kit.LogKit;
import yaolong.base.ar.kit.StrKit;

/**
 *
 * @author
 *
 */
public class Config {
	private final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	private String name;
	DataSource dataSource;

	public Dialect dialect;
	boolean showSql;
	boolean devMode;
	int transactionLevel;
	IContainerFactory containerFactory;
	ICache cache;

	/**
	 * Constructor with full parameters
	 * 
	 * @param name
	 *            the name of the config
	 * @param dataSource
	 *            the dataSource
	 * @param dialect
	 *            the dialect
	 * @param showSql
	 *            the showSql
	 * @param devMode
	 *            the devMode
	 * @param transactionLevel
	 *            the transaction level
	 * @param containerFactory
	 *            the containerFactory
	 * @param cache
	 *            the cache
	 */
	public Config(String name, DataSource dataSource, Dialect dialect, boolean showSql, boolean devMode,
			int transactionLevel, IContainerFactory containerFactory, ICache cache) {
		if (StrKit.isBlank(name))
			throw new IllegalArgumentException("Config name can not be blank");
		if (dataSource == null)
			throw new IllegalArgumentException("DataSource can not be null");
		if (dialect == null)
			throw new IllegalArgumentException("Dialect can not be null");
		if (containerFactory == null)
			throw new IllegalArgumentException("ContainerFactory can not be null");
		if (cache == null)
			throw new IllegalArgumentException("Cache can not be null");

		this.name = name.trim();
		this.dataSource = dataSource;
		this.dialect = dialect;
		this.showSql = showSql;
		this.devMode = devMode;
		this.transactionLevel = transactionLevel;
		this.containerFactory = containerFactory;
		this.cache = cache;
	}

	/**
	 * Constructor with name and dataSource
	 */
	public Config(String name, DataSource dataSource) {
		this(name, dataSource, new MysqlDialect());
	}

	/**
	 * Constructor with name, dataSource and dialect
	 */
	public Config(String name, DataSource dataSource, Dialect dialect) {
		this(name, dataSource, dialect, false, false, DbKit.DEFAULT_TRANSACTION_LEVEL,
				IContainerFactory.defaultContainerFactory, new EhCache());
	}

	private Config() {

	}

	/**
	 * Create broken config for DbKit.brokenConfig =
	 * Config.createBrokenConfig();
	 */
	static Config createBrokenConfig() {
		Config ret = new Config();
		ret.dialect = new MysqlDialect();
		ret.showSql = false;
		ret.devMode = false;
		ret.transactionLevel = DbKit.DEFAULT_TRANSACTION_LEVEL;
		ret.containerFactory = IContainerFactory.defaultContainerFactory;
		ret.cache = new EhCache();
		return ret;
	}

	public String getName() {
		return name;
	}

	public Dialect getDialect() {
		return dialect;
	}

	public ICache getCache() {
		return cache;
	}

	public int getTransactionLevel() {
		return transactionLevel;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public IContainerFactory getContainerFactory() {
		return containerFactory;
	}

	public boolean isShowSql() {
		return showSql;
	}

	public boolean isDevMode() {
		return devMode;
	}

	// --------

	/**
	 * Support transaction with Transaction interceptor
	 */
	public final void setThreadLocalConnection(Connection connection) {
		threadLocal.set(connection);
	}

	public final void removeThreadLocalConnection() {
		threadLocal.remove();
	}

	/**
	 * Get Connection. Support transaction if Connection in ThreadLocal
	 */
	public final Connection getConnection() throws SQLException {
		Connection conn = threadLocal.get();
		if (conn != null)
			return conn;
		return showSql ? new SqlReporter(dataSource.getConnection()).getConnection() : dataSource.getConnection();
	}

	/**
	 * Helps to implement nested transaction. Tx.intercept(...) and Db.tx(...)
	 * need this method to detected if it in nested transaction.
	 */
	public final Connection getThreadLocalConnection() {
		return threadLocal.get();
	}

	/**
	 * Return true if current thread in transaction.
	 */
	public final boolean isInTransaction() {
		return threadLocal.get() != null;
	}

	/**
	 * Close ResultSet、Statement、Connection ThreadLocal support declare
	 * transaction.
	 */
	public final void close(ResultSet rs, Statement st, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				LogKit.error(e.getMessage(), e);
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				LogKit.error(e.getMessage(), e);
			}
		}

		if (threadLocal.get() == null) { // in transaction if conn in
											// threadlocal
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new ActiveRecordException(e);
				}
			}
		}
	}

	public final void close(Statement st, Connection conn) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				LogKit.error(e.getMessage(), e);
			}
		}

		if (threadLocal.get() == null) { // in transaction if conn in
											// threadlocal
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new ActiveRecordException(e);
				}
			}
		}
	}

	public final void close(Connection conn) {
		if (threadLocal.get() == null) // in transaction if conn in threadlocal
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					throw new ActiveRecordException(e);
				}
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
