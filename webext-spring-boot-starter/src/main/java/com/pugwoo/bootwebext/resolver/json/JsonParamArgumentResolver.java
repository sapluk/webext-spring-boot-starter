package com.pugwoo.bootwebext.resolver.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.pugwoo.bootwebext.JsonParam;
import org.springframework.core.Conventions;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;

import static com.pugwoo.bootwebext.resolver.utils.AbstractMessageConverterMethodArgumentResolverUtil.adaptArgumentIfNecessary;
import static com.pugwoo.bootwebext.resolver.utils.AbstractMessageConverterMethodArgumentResolverUtil.isBindExceptionRequired;
import static com.pugwoo.bootwebext.resolver.utils.AbstractMessageConverterMethodArgumentResolverUtil.validateIfApplicable;

/**
 * http://zjumty.iteye.com/blog/1927890
 * 些许改造 2015年8月15日 12:13:01
 * 2018年3月14日 16:52:42 完整支持了泛型，支持1或2个泛型，不支持3个及以上
 *
 * 2022年01月12日 支持泛型，采用springboot的方案，与@RequestBody对json的解析保持一致
 */
public class JsonParamArgumentResolver implements HandlerMethodArgumentResolver {
	
	private static final ObjectMapper OBJECT_MAPPER = new MyObjectMapper(false);

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(JsonParam.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
		parameter = parameter.nestedIfOptional();
		Object arg = parseJsonParam(parameter, webRequest);
		String name = Conventions.getVariableNameForParameter(parameter);
		if (binderFactory != null) {
			WebDataBinder binder = binderFactory.createBinder(webRequest, arg, name);
			if (arg != null) {
				validateIfApplicable(binder, parameter);
				if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(parameter)) {
					throw new MethodArgumentNotValidException(parameter, binder.getBindingResult());
				}
			}
			if (mavContainer != null) {
				mavContainer.addAttribute(BindingResult.MODEL_KEY_PREFIX + name, binder.getBindingResult());
			}
		}
		return adaptArgumentIfNecessary(arg, parameter);
	}
	
	/**
	 * 解析参数对象
	 */
	private Object parseJsonParam(MethodParameter parameter, NativeWebRequest webRequest)
			throws IOException, ClassNotFoundException {
		// 获得@JsonParam注解的value值
		JsonParam jsonParam = parameter.getParameterAnnotation(JsonParam.class);
		String paramName = "";
		if(jsonParam != null) {
			paramName = jsonParam.value();
		}
		
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		if (request == null) {
			throw new RuntimeException("No HttpServletRequest");
		}
		
		String paramValue;
		if(paramName.isEmpty()) {
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
		
		// jsonString -> obj
		Type type = parameter.getNestedGenericParameterType();
		Class<?> clazz = parameter.getContainingClass();
		JavaType javaType = OBJECT_MAPPER.constructType(GenericTypeResolver.resolveType(type, clazz));
		try {
			return OBJECT_MAPPER.readValue(paramValue, javaType);
		} catch (InvalidDefinitionException ex) {
			throw new HttpMessageConversionException("Type definition error: " + ex.getType(), ex);
		} catch (JsonProcessingException ex) {
			throw new HttpMessageNotReadableException("JSON parse error: " + ex.getOriginalMessage(), ex);
		}
	}

}
