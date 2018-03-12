package yaolong.base.mvc.base;

import java.util.Date;

import org.springframework.format.Formatter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory;

/**
 * 
 * @author yaolong
 *
 */
public class DateAnnotationFormatterFactory extends DateTimeFormatAnnotationFormatterFactory {

	@Override
	protected Formatter<Date> getFormatter(DateTimeFormat annotation, Class<?> fieldType) {
		DateFormatter formatter = new BaseDateFormatter();
		formatter.setStylePattern(resolveEmbeddedValue(annotation.style()));
		formatter.setIso(annotation.iso());
		formatter.setPattern(resolveEmbeddedValue(annotation.pattern()));
		return formatter;
	}

}
