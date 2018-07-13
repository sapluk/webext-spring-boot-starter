package com.pugwoo.bootwebext.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

	private static final Logger LOGGER = LoggerFactory.getLogger(StringToLocalDateTimeConverter.class);

	/**
	 * 提供一个方便给使用者调用的转换日期的静态工具方法
	 */
	public static LocalDateTime convertDate(String strDate) {
		strDate = strDate == null ? null : strDate.trim();
		if (strDate == null || strDate.isEmpty()) {
			return null;
		}

		String pattern = DateFormatters.determineDateFormat(strDate);
		if (pattern == null) {
			LOGGER.error("unknown date format for {}", strDate);
			return null;
		}
		DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
		return LocalDateTime.parse(strDate, df);
	}

	@Override
	public LocalDateTime convert(String source) {
		return convertDate(source);
	}

}
