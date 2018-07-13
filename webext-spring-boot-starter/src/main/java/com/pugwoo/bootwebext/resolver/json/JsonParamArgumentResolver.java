package com.pugwoo.bootwebext.resolver.json;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.pugwoo.bootwebext.JsonParam;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

/**
 * http://zjumty.iteye.com/blog/1927890
 * 些许改造 2015年8月15日 12:13:01
 * 2018年3月14日 16:52:42 完整支持了泛型，支持1或2个泛型，不支持3个及以上
 */
public class JsonParamArgumentResolver implements HandlerMethodArgumentResolver {
	
	private static final ObjectMapper OBJECT_MAPPER = new MyObjectMapper();

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(JsonParam.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
		// 获得@JsonParam注解的value值
		JsonParam jsonParam = parameter.getParameterAnnotation(JsonParam.class);
		String paramName = "";
		if(jsonParam != null) {
			paramName = jsonParam.value();
		}
		
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		
		String paramValue;
		if(paramName == null || paramName.isEmpty()) {
	        // 把reqeust的body读取到StringBuilder
	        BufferedReader reader = request.getReader();
	        StringBuilder sb = new StringBuilder();

	        char[] buf = new char[1024];
	        int rd;
	        while((rd = reader.read(buf)) != -1){
	            sb.append(buf, 0, rd);
	        }
	        paramValue = sb.toString();
		} else {
			paramValue = request.getParameter(paramName);
		}
		
		if(paramValue == null || paramValue.trim().isEmpty()) {
			return null;
		}

		String typeName = parameter.getGenericParameterType().getTypeName();
		if(!typeName.contains("<")) {
			return OBJECT_MAPPER.readValue(paramValue, parameter.getParameterType());
		} else { // 处理泛型
			JavaType type = parseGenericType(OBJECT_MAPPER.getTypeFactory(), typeName);
			return OBJECT_MAPPER.readValue(paramValue, type);
		}

	}

	/**
	 * 解析泛型的类,只支持1个或2个的泛型类型，不支持3个及以上的
	 * @param className
	 * @return 如果没有泛型，则返回null
	 * @throws ClassNotFoundException 
	 */
	private static JavaType parseGenericType(TypeFactory typeFactory, String className)
			throws ClassNotFoundException {
		if(className == null) { return null; }
		int left = className.indexOf("<");
		if(left < 0) {
			return typeFactory.constructType(Class.forName(className.trim()));
		}
		int right = className.lastIndexOf(">");
		
		String baseClassName = className.substring(0, left);
		String genericAll = className.substring(left + 1, right);
		
		assertLessThan3Dot(genericAll);
		int dotIndex = getDotIndex(genericAll);
		if(dotIndex < 0) {
			return typeFactory.constructParametricType(Class.forName(baseClassName.trim()),
					parseGenericType(typeFactory, genericAll));
		} else {
			String leftClassName = genericAll.substring(0, dotIndex);
			String rightClassName = genericAll.substring(dotIndex + 1);
			return typeFactory.constructParametricType(Class.forName(baseClassName.trim()),
					parseGenericType(typeFactory, leftClassName),
					parseGenericType(typeFactory, rightClassName));
		}
	}
	
	private static int getDotIndex(String str) {
		if(str == null) { return -1; }
		int bracket = 0;
		for(int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if(c == ',' && bracket == 0) {
				return i;
			}
			if(c == '<') {
				bracket++;
			} else if(c == '>') {
				bracket--;
			}
		}
		return -1;
	}
	
	private static void assertLessThan3Dot(String str) {
		if(str == null) { return; }
		int counts = 0;
		int bracket = 0;
		for(int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if(c == ',' && bracket == 0) {
				counts++;
			}
			if(c == '<') {
				bracket++;
			} else if(c == '>') {
				bracket--;
			}
		}
		if(counts > 1) {
			throw new RuntimeException("spring-boot-conf not support more than two generic type, found " + (counts+1)
				+ " for class:" +str);
		}
	}

}
