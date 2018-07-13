package com.pugwoo.bootwebext;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * https://stackoverflow.com/questions/8986593/how-to-customize-parameter-names-when-binding-spring-mvc-command-objects
 * @author jkee pugwoo
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamName {

    /**
     * The name of the request parameter to bind to.
     * request参数名称，支持指定多个参数名称。
     * 当指定了多个参数时，会按顺序选择第一个非空字符串的参数。
     */
    String[] value();
    
}
