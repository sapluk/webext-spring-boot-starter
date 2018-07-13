package com.pugwoo.webextstarterdemo.web;

import com.pugwoo.bootwebext.JsonParam;
import com.pugwoo.webextstarterdemo.bean.ErrorCode;
import com.pugwoo.webextstarterdemo.bean.WebJsonBean;
import com.pugwoo.webextstarterdemo.entity.UserDO;
import com.pugwoo.webextstarterdemo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试 @JsonParam 自动解析json字符串为参数 <br/>
 * 注解@JsonParam 用于接收前端的json字符串，并自动转换为Java Bean对象
 * @date 2018-07-12
 */
@RestController
public class TestJsonParamController {

    @Autowired
    private IUserService userService;

    /** 测试JsonParam
     *      /testJsonParam?test={"name":"abc","password":123456,"age":16}
     *      上述请求需进行转码，响应转码为:
     *  http://127.0.0.1:8080/testJsonParam?test=%7b%22name%22%3a%22abc%22%2c%22password%22%3a123456%2c%22age%22%3a16%7d
     */
    @RequestMapping("/testJsonParam")
    public WebJsonBean testJsonParam(@JsonParam("test") UserDO userDO){
        boolean success = userService.insertOrUpdate(userDO);
        return success ? new WebJsonBean(userDO) : new WebJsonBean(ErrorCode.COMMON_BIZ_ERROR);
    }

}
