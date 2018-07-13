package com.pugwoo.webextstarterdemo.web;

import com.pugwoo.webextstarterdemo.bean.WebJsonBean;
import com.pugwoo.webextstarterdemo.vo.UserForm;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 演示注解 @ParamName ，用于指定前端传入VO时，VO中参数和变量的关系
 * @date 2018-07-12
 */
@RestController
public class TestParamNameController {

	/**
	 * 测试链接：
	 * http://127.0.0.1:8080/testParamName?name=nick&addr=sz
	 * 等价于
	 * http://127.0.0.1:8080/testParamName?name=nick&address=sz
	 * @param userForm
	 * @return
	 */
	@RequestMapping("/testParamName")
	public WebJsonBean testParamName(UserForm userForm) {
		return new WebJsonBean(userForm);
	}
	
}
