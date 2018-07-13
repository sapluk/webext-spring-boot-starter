package com.pugwoo.webextstarterdemo.bean;

/**
 *  错误代码接口<br/>
 * @date 2018-07-12
 */
public interface IErrorCode {

    /**
     * 获取错误代码
     * @return 错误代码值
     */
    int getCode();

    /**
     * 获取代码名称
     * @return 错误代码名称
     */
    String getName();

}
