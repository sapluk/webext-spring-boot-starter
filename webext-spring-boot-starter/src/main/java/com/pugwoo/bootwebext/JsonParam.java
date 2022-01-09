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
     *    当该值提供时，从url/x-www-form-urlencoded/multipart/form-data等读取参数
     *    当该值未提供时，从body中读取json数据，此时不能与@RequestBody共用，因为该流只能读取一次
     * @return request变量名
     */
    String value() default "";
}
