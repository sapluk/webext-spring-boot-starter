package com.pugwoo.bootwebext.converter;

import java.util.HashMap;
import java.util.Map;

public class Formatters {

    /**
     * 该字段请忽略 FindBugs-Field is a mutable collection 警告，
     * 设置为public是允许使用者扩展需要的匹配规则
     */
	@SuppressWarnings("serial")
	public static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
		put("^\\d{6}$", "yyyyMM");                       // 201703
	    put("^\\d{8}$", "yyyyMMdd");                     // 20170306
	    put("^\\d{14}$", "yyyyMMddHHmmss");              // 20170306152356
	    put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");     // 20170306 152356
	    put("^\\d{4}-\\d{1,2}$", "yyyy-MM");             // 2017-03
	    put("^\\d{4}/\\d{1,2}$", "yyyy/MM");             // 2017/03
	    put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd"); // 2017-03-06
	    put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd"); // 2017/03/06
	    put("^\\d{1,2}:\\d{1,2}:\\d{1,2}$", "HH:mm:ss"); // 16:34:32
	    put("^\\d{1,2}:\\d{1,2}$", "HH:mm");             // 16:34
	    put("^\\d{4}年\\d{1,2}月\\d{1,2}日$", "yyyy年MM月dd日"); // 2017年3月30日
	    put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}$", "yyyy-MM-dd HH:mm"); // 2017-03-06 15:23
	    put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{1,2}$", "yyyy/MM/dd HH:mm"); // 2017/03/06 15:23
	    put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}$", "yyyy-MM-dd HH:mm:ss"); // 2017-03-06 15:23:56
	    put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}$", "yyyy/MM/dd HH:mm:ss"); // 2017/03/06 15:23:56
	    put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}$", "yyyy-MM-dd HH:mm:ss.SSS");   // 2017-10-18 16:00:00.000
	    put("^\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}Z$", "yyyy-MM-dd'T'HH:mm:ss.SSSX"); // 2017-10-18T16:00:00.000Z
	    put("^\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d{1,3}$", "yyyy-MM-dd'T'HH:mm:ss.SSS");   // 2017-10-18T16:00:00.000
	}};
	
	/**
	 * 检查字符串的时间格式，返回null表示匹配不到格式
	 * @param dateString
	 * @return
	 */
	public static String determineDateFormat(String dateString) {
		for (Map.Entry<String, String> e : DATE_FORMAT_REGEXPS.entrySet()) {
			if (dateString.matches(e.getKey())) {
				return e.getValue();
			}
		}
		return null; // Unknown format
	}
	
}
