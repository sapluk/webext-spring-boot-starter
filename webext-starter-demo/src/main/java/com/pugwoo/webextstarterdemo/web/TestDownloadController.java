package com.pugwoo.webextstarterdemo.web;

import com.pugwoo.bootwebext.bean.DownloadBean;
import com.pugwoo.bootwebext.bean.StreamDownloadBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 测试 DownloadBean <br/>
 *  用于下载文件
 * @date 2018-07-12
 */
@RestController
public class TestDownloadController {

	/**
	 * 127.0.0.1:8080/testDownloadBean
	 * @return
	 */
	@RequestMapping("/testDownloadBean")
	public DownloadBean testDownloadBean() {
		return new DownloadBean("testDownloadBean.txt", "hello world你好");
	}

	/**
	 * 127.0.0.1:8080/testStreamDownloadBean_1
	 * @return
	 */
	@RequestMapping("/testStreamDownloadBean_1")
	public StreamDownloadBean testStreamDownloadBean1() {
		return new StreamDownloadBean("testStreamDownloadBean_1.txt",
				new ByteArrayInputStream("hello world!你好".getBytes()));
	}

	/** 输出静态文件
	 * 127.0.0.1:8080/testStreamDownloadBean_2
	 */
    @RequestMapping("/testStreamDownloadBean_2")
    public StreamDownloadBean testStreamDownloadBean2() {
        InputStream resourceAsStream = getClass().getResourceAsStream("/static/smile.jpg");
        return new StreamDownloadBean("smile.jpg", resourceAsStream);
    }

}
