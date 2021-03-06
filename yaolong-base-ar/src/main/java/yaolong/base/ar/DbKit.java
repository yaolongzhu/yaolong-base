package yaolong.base.ar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


/**
*
* @author
*
*/
@SuppressWarnings("rawtypes")
public class DbKit {
	/**
	 * The main Config object for system
	 */
	public static Config config = null;
	
	/**
	 * 1: For ActiveRecordPlugin.useAsDataTransfer(...) 用于分布式场景
	 * 2: For Model.getAttrsMap()/getModifyFlag() and Record.getColumnsMap()
	 * while the ActiveRecordPlugin not start or the Exception throws of HashSessionManager.restorSession(..) by Jetty
	 */
	static Config brokenConfig = Config.createBrokenConfig();
	
	private static Map<Class<? extends Model>, Config> modelToConfig = new HashMap<Class<? extends Model>, Config>();
	private static Map<String, Config> configNameToConfig = new HashMap<String, Config>();
	
	public static final Object[] NULL_PARA_ARRAY = new Object[0];
	public static final String MAIN_CONFIG_NAME = "main";
	public static final int DEFAULT_TRANSACTION_LEVEL = Connection.TRANSACTION_REPEATABLE_READ;
	
	private DbKit() {}
	
	/**
	 * Add Config object
	 * @param config the Config contains DataSource, Dialect and so on
	 */
	public static void addConfig(Config config) {
		if (config == null)
			throw new IllegalArgumentException("Config can not be null");
		if (configNameToConfig.containsKey(config.getName()))
			throw new IllegalArgumentException("Config already exists: " + config.getName());
		
		configNameToConfig.put(config.getName(), config);
		
		/** 
		 * Replace the main config if current config name is MAIN_CONFIG_NAME
		 */
		if (MAIN_CONFIG_NAME.equals(config.getName()))
			DbKit.config = config;
		
		/**
		 * The configName may not be MAIN_CONFIG_NAME,
		 * the main config have to set the first comming Config if it is null
		 */
		if (DbKit.config == null)
			DbKit.config = config;
	}
	
	static void addModelToConfigMapping(Class<? extends Model> modelClass, Config config) {
		modelToConfig.put(modelClass, config);
	}
	
	public static Config getConfig() {
		return config;
	}
	
	public static Config getConfig(String configName) {
		return configNameToConfig.get(configName);
	}
	
	public static Config getConfig(Class<? extends Model> modelClass) {
		return modelToConfig.get(modelClass);
	}
	
	public static final void close(ResultSet rs, Statement st) {
		if (rs != null) {try {rs.close();} catch (SQLException e) {throw new ActiveRecordException(e);}}
		if (st != null) {try {st.close();} catch (SQLException e) {throw new ActiveRecordException(e);}}
	}
	
	public static final void close(Statement st) {
		if (st != null) {try {st.close();} catch (SQLException e) {throw new ActiveRecordException(e);}}
	}
	
	public static Config removeConfig(String configName) {
		if (DbKit.config != null && DbKit.config.getName().equals(configName))
			throw new RuntimeException("Can not remove the main config.");
		
		DbPro.removeDbProWithConfig(configName);
		return configNameToConfig.remove(configName);
	}
	
	@SuppressWarnings("unchecked")
	public static Class<? extends Model> getUsefulClass(Class<? extends Model> modelClass) {
		// com.demo.blog.Blog$$EnhancerByCGLIB$$69a17158
		return (Class<? extends Model>)((modelClass.getName().indexOf("EnhancerByCGLIB") == -1 ? modelClass : modelClass.getSuperclass()));
	}
}


