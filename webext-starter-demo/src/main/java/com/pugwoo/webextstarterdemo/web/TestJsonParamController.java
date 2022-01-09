package com.pugwoo.webextstarterdemo.web;

import com.pugwoo.bootwebext.JsonParam;
import com.pugwoo.webextstarterdemo.bean.WebJsonBean;
import com.pugwoo.webextstarterdemo.entity.UserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 测试 @JsonParam 自动解析json字符串为参数 <br/>
 * 注解@JsonParam 用于接收前端的json字符串，并自动转换为Java Bean对象
 * @date 2018-07-12
 */
@RestController
public class TestJsonParamController {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(TestJsonParamController.class);

    /**
     * 测试 @JsonParam，指定了value值，可采用url, x-www-form-urlencoded, multipart/form-data
     * 测试见 http.testJsonParam.http
     */
    @RequestMapping("/testJsonParam")
    public WebJsonBean testJsonParam(@JsonParam("test") UserDO userDO){
        LOGGER.debug("testJsonParam: {}", userDO);
        return new WebJsonBean(userDO);
    }
    
    /**
     * 测试 @JsonParam，未指定value值，可采用json
     * 测试见 http.testJsonParam.http
     */
    @RequestMapping("/testJsonParamWithoutValue")
    public WebJsonBean testJsonParamWithoutValue(@JsonParam UserDO userDO){
        LOGGER.debug("testJsonParamWithoutValue: {}", userDO);
        return new WebJsonBean(userDO);
    }
    
    // ---------------------------------------------------------------------------
    
    /**
     * 测试 @Valid @JsonParam
     * 测试见 http.testJsonParamValid.http
     */
    @RequestMapping("/testValidJsonParam")
    public WebJsonBean testValidJsonParam(@Valid @JsonParam UserDO userDO){
        LOGGER.debug("testValidJsonParam: {}", userDO);
        return new WebJsonBean(userDO);
    }
    
    /**
     * 测试 @Validated @JsonParam
     * 测试见 http.testJsonParamValid.http
     */
    @RequestMapping("/testValidatedJsonParam")
    public WebJsonBean testValidatedJsonParam(@Validated @JsonParam UserDO userDO){
        LOGGER.debug("testValidatedJsonParam: {}", userDO);
        return new WebJsonBean(userDO);
    }
    
    /**
     * 测试 @Valid @RequestBody 主要用于对比@JsonParam
     * 测试见 http.testJsonParamValid.http
     */
    @RequestMapping("/testValidRequestBody")
    public WebJsonBean testValidRequestBody(@Valid @RequestBody UserDO userDO){
        LOGGER.debug("testValidRequestBody: {}", userDO);
        return new WebJsonBean(userDO);
    }
    
    /**
     * 测试 @Validated @RequestBody 主要用于对比@JsonParam
     * 测试见 http.testJsonParamValid.http
     */
    @RequestMapping("/testValidatedRequestBody")
    public WebJsonBean testValidatedRequestBody(@Validated @RequestBody UserDO userDO){
        LOGGER.debug("testValidatedRequestBody: {}", userDO);
        return new WebJsonBean(userDO);
    }
}
