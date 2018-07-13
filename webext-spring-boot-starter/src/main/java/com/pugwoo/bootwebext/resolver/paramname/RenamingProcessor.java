package com.pugwoo.bootwebext.resolver.paramname;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import com.pugwoo.bootwebext.ParamName;

/**
 * https://stackoverflow.com/questions/8986593/how-to-customize-parameter-names-when-binding-spring-mvc-command-objects
 * @author jkee pugwoo
 */
public class RenamingProcessor extends ServletModelAttributeMethodProcessor {
	
	public static class ParamFieldMap {
		public String[] paramName;
		public String fieldName;
	}

	// Rename cache
	private final Map<Class<?>, List<ParamFieldMap>> replaceMap = new ConcurrentHashMap<Class<?>, List<ParamFieldMap>>();

	public RenamingProcessor(boolean annotationNotRequired) {
		super(annotationNotRequired);
	}

	@Override
	protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest nativeWebRequest) {
		Object target = binder.getTarget();
		Class<?> targetClass = target.getClass();
		if (!replaceMap.containsKey(targetClass)) {
			List<ParamFieldMap> mapping = analyzeClass(targetClass);
			replaceMap.put(targetClass, mapping);
		}
		List<ParamFieldMap> mapping = replaceMap.get(targetClass);
		ParamNameDataBinder paramNameDataBinder = new ParamNameDataBinder(target, binder.getObjectName(), mapping);
		super.bindRequestParameters(paramNameDataBinder, nativeWebRequest);
	}

	private static List<ParamFieldMap> analyzeClass(Class<?> targetClass) {
		Field[] fields = targetClass.getDeclaredFields();
		List<ParamFieldMap> result = new ArrayList<RenamingProcessor.ParamFieldMap>();
		for (Field field : fields) {
			ParamName paramNameAnnotation = field.getAnnotation(ParamName.class);
			if (paramNameAnnotation != null && paramNameAnnotation.value().length > 0) {
				ParamFieldMap paramFieldMap = new ParamFieldMap();
				paramFieldMap.paramName = paramNameAnnotation.value();
				paramFieldMap.fieldName = field.getName();
				result.add(paramFieldMap);
			}
		}
		return result;
	}

}
