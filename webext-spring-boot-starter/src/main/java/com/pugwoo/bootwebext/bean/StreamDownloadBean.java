package com.pugwoo.bootwebext.bean;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class StreamDownloadBean extends ResponseEntity<InputStreamResource> {
	
	/**
	 * 构造一个下载Bean
	 * @param filename 下载文件的保存名称
	 * @param in 输入流
	 */
	public StreamDownloadBean(String filename, InputStream in) {
		super(new InputStreamResource(in), getHeaders(filename), HttpStatus.OK);
	}
	
	/**
	 * 构造一个下载Bean
	 * @param filename 下载文件的保存名称
	 * @param in 输入流
	 * @param headers 自定义头部
	 */
	public StreamDownloadBean(String filename, InputStream in, Map<String, String> headers) {
		super(new InputStreamResource(in), getHeaders(filename, headers), HttpStatus.OK);
	}

	/**
	 * 构造一个下载Bean
	 * @param filename 下载文件的保存名称
	 * @param in 输入流
	 * @param headers 自定义头部
	 * @param status 自定义响应状态
	 */
	public StreamDownloadBean(String filename, InputStream in, Map<String, String> headers, HttpStatus status) {
		super(new InputStreamResource(in), getHeaders(filename, headers), status);
	}
	
	private static HttpHeaders getHeaders(String filename) {
		return getHeaders(filename, null);
	}
	
	private static HttpHeaders getHeaders(String filename, Map<String, String> headers) {
		HttpHeaders h = new HttpHeaders();
		if (filename != null && (headers == null || headers.get("Content-Disposition") == null)) {
			try {
				filename = URLEncoder.encode(filename, "UTF-8");
			} catch (UnsupportedEncodingException e) { // ignore
			}
			h.add("Content-Disposition", "attachment;filename=" + filename);
		}
		
		if(headers != null) {
			for(Entry<String, String> entry : headers.entrySet()) {
				h.add(entry.getKey(), entry.getValue());
			}
		}
		
		return h;
	}

}
