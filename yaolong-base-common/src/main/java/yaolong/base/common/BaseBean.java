package yaolong.base.common;

import java.beans.PropertyDescriptor;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import yaolong.base.ar.Model;

/**
 * @author
 * 
 *         提供Model与Bean之间属性值的复制
 * 
 * @param <T>
 * @param <M>
 */
@SuppressWarnings("rawtypes")
public class BaseBean<T extends BaseBean, M extends Model<?>> {
	private static final Log log = LogFactory.getLog(BaseBean.class);

	/**
	 * 将Model对象的数据转移到Bean对象中<br>
	 * Bean中的属性要与Model中的属性名称相同
	 * 
	 * @param t:Bean
	 * @param m:Model
	 * @return
	 * @throws Exception
	 */
	public T revertModel2Bean(M m, T t) throws Exception {
		if (t != null && m != null) {
			PropertyDescriptor[] propertyDess = PropertyUtils.getPropertyDescriptors(t);
			if (ArrayUtils.isNotEmpty(propertyDess)) {
				for (PropertyDescriptor propertyDescriptor : propertyDess) {
					String propertyName = propertyDescriptor.getName();
					Object mProObj = PropertyUtils.getProperty(m, propertyName);
					// 如果Model中的属性为字符串
					if (mProObj instanceof String && !propertyName.equals("tableName")
							&& !propertyName.equals("class")) {
						Object proObj = PropertyUtils.getProperty(t, propertyName);
						String mproValue = BeanUtils.getProperty(m, propertyName);
						if (proObj instanceof Integer) {
							Integer intValue = m.getInt(mproValue);
							if (intValue != null) {
								BeanUtils.setProperty(t, propertyName, intValue);
							}
						} else if (proObj instanceof Double) {
							Double dobValue = m.getDouble(mproValue);
							if (dobValue != null) {
								BeanUtils.setProperty(t, propertyName, dobValue);
							}
						} else if (proObj instanceof Date) {
							Timestamp timestamp = m.getTimestamp(mproValue);
							Date datValue = new Date();
							if (timestamp != null) {
								datValue = new Date(timestamp.getTime());
							}
							if (datValue != null) {
								BeanUtils.setProperty(t, propertyName, datValue);
							}
						} else if (proObj instanceof String) {
							String strValue = m.getStr(mproValue);
							if (strValue != null) {
								BeanUtils.setProperty(t, propertyName, strValue);
							}
						} else if (proObj instanceof Long) {
							Long longValue = m.getLong(mproValue);
							if (longValue != null) {
								BeanUtils.setProperty(t, propertyName, longValue);
							}
						} else {
							log.info("不支持的Bean数据类型，请修改代码。");
						}
					} else {
						log.info("不支持Model的属性");
					}
				}
				return t;
			}
		}
		return null;
	}

	/**
	 * 将Bean中值转移到Model中
	 * 
	 * @param t
	 * @param m
	 * @return
	 * @throws Exception
	 */
	public M revertBean2Model(T t, M m) throws Exception {
		if (t != null && m != null) {
			PropertyDescriptor[] proDess = PropertyUtils.getPropertyDescriptors(t);
			if (ArrayUtils.isNotEmpty(proDess)) {
				for (PropertyDescriptor pro : proDess) {
					String propertyName = pro.getName();
					if (!StringUtils.equals(propertyName, "class")) {
						String proValue = BeanUtils.getProperty(t, propertyName);
						Object proObj = PropertyUtils.getProperty(t, propertyName);
						if (proObj instanceof String) {
							m.set(propertyName, proValue);
						} else if (proObj instanceof Integer) {
							Integer intValue = 0;
							try {
								intValue = Integer.parseInt(proValue);
							} catch (NumberFormatException e) {
								intValue = 0;
							}
							m.set(propertyName, intValue);
						} else if (proObj instanceof Date) {
							if (StringUtils.isNotEmpty(proValue)) {
								Timestamp timestamp = Timestamp.valueOf(proValue);
								m.set(propertyName, timestamp);
							}
						} else if (proObj instanceof Long) {
							Long lng = new Long(proValue);
							m.set(propertyName, lng);
						} else if (proObj instanceof Double) {
							Double dob = new Double(proValue);
							m.set(propertyName, dob);
						} else {
							log.info("无法支持的数据类型");
						}
					}
				}
			}
		}
		return null;
	}
}
