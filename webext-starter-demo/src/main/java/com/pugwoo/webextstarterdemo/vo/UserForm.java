package com.pugwoo.webextstarterdemo.vo;

import com.pugwoo.bootwebext.ParamName;
import lombok.Data;

/**
 * 用于 @ParamName 演示用
 * @date 2018-07-12
 */
@Data
public class UserForm {

	private String name;
	
	@ParamName("addr")
	private String address;

	// getter/setter...
}
