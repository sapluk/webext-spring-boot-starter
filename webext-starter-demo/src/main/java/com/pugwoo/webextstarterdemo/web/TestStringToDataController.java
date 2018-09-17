package com.pugwoo.webextstarterdemo.web;

import com.pugwoo.webextstarterdemo.bean.ErrorCode;
import com.pugwoo.webextstarterdemo.bean.WebJsonBean;
import com.pugwoo.webextstarterdemo.web.form.MyForm;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 自动解析日期<br/>
 * 支持自动将String类型解析为Date类型，支持多种格式。该方式同时支持@RequestParameter的注入和@JsonParam方式的注入
 * @date 2018-06-22
 */
@RestController
public class TestStringToDataController {

    /**
     * http://127.0.0.1:8080/testStringToData?time=20180808
     * @param time
     * @return
     */
    @RequestMapping("/testStringToData")
    public WebJsonBean testStringToData(Date time){
        if (time != null) {
            return new WebJsonBean(time);
        }
        return new WebJsonBean(ErrorCode.MISSING_PARAMETERS,"time参数未提供或者格式错误，请确保time参数正确提供，如 ?time=20180808");
    }

    /**
     * http://127.0.0.1:8080/testStringToDate2?myDate=20180808
     *
     * 发现问题：对于对象的形式，String没有转成Date
     * 但是这个来自于spring自身，在以前的4.x版本中没有这个问题
     * @param form
     * @return
     */
    @RequestMapping("/testStringToDate2")
    public WebJsonBean testStringDate2(MyForm form) {
        if(form == null) {
            return new WebJsonBean(ErrorCode.COMMON_BIZ_ERROR, "form is null");
        }
        if(form.getMyDate() == null) {
            return new WebJsonBean(ErrorCode.COMMON_BIZ_ERROR, "form.myDate is null");
        }
        return new WebJsonBean(form.getMyDate());
    }
}
