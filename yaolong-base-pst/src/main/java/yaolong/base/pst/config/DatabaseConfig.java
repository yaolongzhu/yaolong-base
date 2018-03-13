package yaolong.base.pst.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import yaolong.base.ar.ActiveRecordPlugin;
import yaolong.base.ar.Model;
import yaolong.base.ar.kit.StrKit;
import yaolong.base.ar.listener.ArListener;
import yaolong.base.id.IdWorker;
import yaolong.base.pst.ArConfig;
import yaolong.base.pst.annotation.Tab;
import yaolong.base.pst.util.Db;

public class DatabaseConfig implements InitializingBean, DisposableBean, ApplicationContextAware {

    /**
     * 
     */
    protected final Logger log = Logger.getLogger(getClass());

    /**
     * 
     */
    private ActiveRecordPlugin arp;

    // private DbPro dbPro;

    private ArConfig config;

    /**
     * 
     */
    private static ApplicationContext applicationContext;

    /**
     * 
     */
    private DataSource dataSource;

    /**
     * 
     */
    // private Dialect dialect;

    /**
     * 
     */
    private boolean showSql;

    public void setDataSource(DataSource dataSource) {
        if (null == dataSource) {
            throw new IllegalArgumentException("DataSource can not be null");
        }
        this.dataSource = dataSource;
    }

    // public void setDialect(Dialect dialect) {
    // if (null == dialect) {
    // log.warn("Using mysql dialect as default.");
    // dialect = new MysqlDialect();// 默认mysql方言
    // }
    // this.dialect = dialect;
    // }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    public void setConfig(ArConfig config) {
        this.config = config;
    }

    /**
     * @param tableName
     * @param primaryKey
     */
    public void setPrimaryKey(String tableName, String primaryKey) {
        arp.setPrimaryKey(tableName, primaryKey);
    }

    @SuppressWarnings({
        "rawtypes", "unchecked"
    })
    @Override
    public void afterPropertiesSet() throws Exception {
        arp = new ActiveRecordPlugin(dataSource);
        // arp.setContainerFactory(new CaseInsensitiveContainerFactory());
        arp.setDialect(config.getDialect());
        arp.setShowSql(showSql);
        arp.setDbPro(config.getDbPro());

        Map<String, Class> modelClazzs = new HashMap<String, Class>();
        Map<String, Object> model = applicationContext.getBeansWithAnnotation(yaolong.base.pst.annotation.Tab.class);
        Class modelClass = null;
        for (Object object: model.values()) {
            modelClass = object.getClass();
            Class superClass = modelClass.getSuperclass();
            if (null == superClass || superClass != Model.class) {
                log.warn(modelClass + " should extends com.jfinal.plugin.activerecord.Model");
                continue;
            }
            Tab tb = (Tab) modelClass.getAnnotation(Tab.class);// 获取model对应的表信息
            if (tb != null) {
                String tableName = tb.value();
                if (StrKit.notBlank(tableName)) {
                    String pkName = tb.pk();
                    if (StrKit.notBlank(pkName)) {
                        arp.addMapping(tableName, pkName, modelClass);
                    } else {
                        arp.addMapping(tableName, modelClass);
                    }
                    Db.setIdWorker(tableName, (IdWorker) applicationContext.getBean("base_idWorker"));
                    modelClazzs.put(tableName, modelClass);
                }
            }
        }
        arp.start();
        for (Entry<String, Class> entry: modelClazzs.entrySet()) {
            Db.setTableAttrs(entry.getKey(), Db.getAttrs(entry.getValue()));
        }
        // 监听消息
        Map<String, ArListener> lis = applicationContext.getBeansOfType(ArListener.class);
        for (ArListener li: lis.values()) {
            try {
                li.started();
            } catch (Exception e) {
                log.error("AR启动监听异常.", e);
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DatabaseConfig.applicationContext = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        if (arp != null) {
            arp.stop();
        }
    }

    // public void setDbPro(DbPro dbPro) {
    // this.dbPro = dbPro;
    // }

}
