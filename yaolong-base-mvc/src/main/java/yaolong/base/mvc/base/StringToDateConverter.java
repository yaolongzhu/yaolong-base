package yaolong.base.mvc.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import yaolong.base.common.exception.BaseException;

/**
 * 
 * @author yaolong
 *
 *         通用日期解析转换器。 默认支持三种格式："yyyy-MM-dd"、"yyyy-MM-dd HH:mm:ss"和数值字符串。
 *
 */
public class StringToDateConverter implements Converter<String, Date> {
	private static final Log log = LogFactory.getLog(StringToDateConverter.class);

	@Override
	public Date convert(String source) {
		if (StringUtils.hasText(source)) {
			try {
				if (source.contains(":")) {
					return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(source);
				} else if (source.contains("-")) {
					return new SimpleDateFormat("yyyy-MM-dd").parse(source);
				} else if (source.matches("\\d+")) {
					return new Date(Long.parseLong(source));
				}
			} catch (NumberFormatException e) {
				log.error("日期解析时发生错误。", e);
				throw new BaseException("日期解析时发生错误。", e);
			} catch (ParseException e) {
				log.error("日期解析时发生错误。", e);
				throw new BaseException("日期解析时发生错误。", e);
			}
			throw new BaseException("无法解析日期:" + source);
		}
		return null;
	}
}
