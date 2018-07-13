package com.pugwoo.bootwebext.resolver.paramname;

import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import com.pugwoo.bootwebext.resolver.paramname.RenamingProcessor.ParamFieldMap;

/**
 * https://stackoverflow.com/questions/8986593/how-to-customize-parameter-names-when-binding-spring-mvc-command-objects
 * @author jkee pugwoo
 */
public class ParamNameDataBinder extends ExtendedServletRequestDataBinder {

    private final List<ParamFieldMap> renameMapping;

    public ParamNameDataBinder(Object target, String objectName, List<ParamFieldMap> renameMapping) {
        super(target, objectName);
        this.renameMapping = renameMapping;
    }

    @Override
    protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request) {
        super.addBindValues(mpvs, request);
        
        for(ParamFieldMap map : renameMapping) {
            String[] fromList = map.paramName;
            String to = map.fieldName;
        	boolean isSet = false;
        	boolean isString = false;
        	for(String from : fromList) {
        		if (mpvs.contains(from)) {
        			Object v = mpvs.getPropertyValue(from).getValue();
        			if(v == null || (v instanceof String && ((String)v).isEmpty())) {
        				isString = v instanceof String;
        				continue;
        			}
        			mpvs.add(to, v);
        			isSet = true;
        			break;
        		}
        	}
        	if(!isSet && isString) {
        		mpvs.add(to, "");
        	}
        }
    }
    
}
