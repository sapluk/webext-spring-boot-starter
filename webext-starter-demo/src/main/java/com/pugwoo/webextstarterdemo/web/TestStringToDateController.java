package com.pugwoo.webextstarterdemo.web;

import com.pugwoo.webextstarterdemo.bean.ErrorCode;
import com.pugwoo.webextstarterdemo.bean.WebJsonBean;
import com.pugwoo.webextstarterdemo.web.form.MyForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 自动解析日期<br/>
 * 支持自动将String类型解析为Date类型，支持多种格式。该方式同时支持@RequestParameter的注入和@JsonParam方式的注入
 * @date 2018-06-22
 */
@RestController
public class TestStringToDateController {

    /**
     * http://127.0.0.1:8080/testStringToDate?time=20180808
     * @param time
     * @return
     */
    @RequestMapping("/testStringToDate")
    public WebJsonBean testStringToDate(Date time){
        if (time != null) {
            return new WebJsonBean(time);
        }
        return new WebJsonBean(ErrorCode.MISSING_PARAMETERS,"time参数未提供或者格式错误，请确保time参数正确提供，如 ?time=20180808");
    }

    /**
     * http://127.0.0.1:8080/testStringToDate2?myDate=20180808
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


	/**
	 * http://127.0.0.1:8080/testStringToDate3
	 * {
	 *     "myDate": "20190525123456"
	 * }
	 * @param form
	 * @return
	 */
    // TODO 这个有bug
    @PostMapping("/testStringToDate3")
    public WebJsonBean testStringDate3(@RequestBody MyForm form) {
        if(form == null) {
            return new WebJsonBean(ErrorCode.COMMON_BIZ_ERROR, "form is null");
        }
        if(form.getMyDate() == null) {
            return new WebJsonBean(ErrorCode.COMMON_BIZ_ERROR, "form.myDate is null");
        }
        return new WebJsonBean(form.getMyDate());
    }
}
