package com.pugwoo.bootwebext.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * 将参数转化为Date。
 * 2017年9月14日 10:40:07 只保留最常用的，不考虑外国人习惯。
 */
public class StringToDateConverter implements Converter<String, Date> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StringToDateConverter.class);

	/**
	 * 提供一个方便给使用者调用的转换日期的静态工具方法
	 */
	public static Date convertDate(String strDate) {
		strDate = strDate == null ? null : strDate.trim();
		if(strDate == null || strDate.isEmpty()) {
			return null;
		}
		
		try {
			String pattern = Formatters.determineDateFormat(strDate);
			if(pattern == null) {
				LOGGER.error("unknown date format for {}", strDate);
				return null;
			}
			return new SimpleDateFormat(pattern).parse(strDate);
		} catch (ParseException e) {
			LOGGER.error("parse date:{} error, msg:{}", strDate, e.getMessage(), e);
			return null;
		}
	}
	
	@Override
	public Date convert(String source) {
		return convertDate(source);
	}

}
