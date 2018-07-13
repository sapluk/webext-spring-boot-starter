package com.pugwoo.bootwebext;

import java.lang.annotation.*;

/**
 * http://zjumty.iteye.com/blog/1927890
 * 些许改造 2015年8月15日 12:13:05
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonParam {

    /**
     * request变量名
     * @return request变量名
     */
    String value() default "";
}
