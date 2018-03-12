package yaolong.base.mvc.base;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.datetime.DateFormatter;

/**
 * 
 * @author yaolong
 *
 */
public class BaseDateFormatter extends DateFormatter {

	@Override
	public Date parse(String text, Locale locale) throws ParseException {
		Date ret = null;
		try {
			ret = super.parse(text, locale);
		} catch (ParseException e) {
			if (text != null && text.matches("\\d+")) {
				ret = new Date(Long.parseLong(text));
			} else {
				throw e;
			}
		}
		return ret;
	}

}
