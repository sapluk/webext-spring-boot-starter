package com.pugwoo.webextstarterdemo.web;

import com.pugwoo.webextstarterdemo.bean.ErrorCode;
import com.pugwoo.webextstarterdemo.bean.WebJsonBean;
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

    @RequestMapping("/testStringToData")
    public WebJsonBean testStringToData(Date time){
        if (time != null) {
            return new WebJsonBean(time);
        }
        return new WebJsonBean(ErrorCode.MISSING_PARAMETERS,"time参数未提供或者格式错误，请确保time参数正确提供，如 ?time=20180808");
    }
}
