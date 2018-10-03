package com.pugwoo.bootwebext.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DownloadBean extends ResponseEntity<byte[]> {

	/**
	 * 构造一个下载Bean
	 * @param filename 下载文件的保存名称
	 * @param bytes 下载的二进制内容
	 */
	public DownloadBean(String filename, byte[] bytes) {
		super(bytes, getHeaders(filename), HttpStatus.OK);
	}
	
	/**
	 * 构造一个下载bean
	 * @param filename
	 * @param content 按照系统默认编码(utf-8)转成byte[]
	 */
	public DownloadBean(String filename, String content) {
		super(content == null ? new byte[0] : content.getBytes(),
				getHeaders(filename), HttpStatus.OK);
	}
	
	/**
	 * 构造一个下载Bean
	 * @param filename 下载文件的保存名称
	 * @param bytes 下载的二进制内容
	 */
	public DownloadBean(String filename, byte[] bytes, Map<String, String> headers) {
		super(bytes, getHeaders(filename, headers), HttpStatus.OK);
	}
	
	/**
	 * 构造一个下载bean
	 * @param filename
	 * @param content 按照系统默认编码(utf-8)转成byte[]
	 */
	public DownloadBean(String filename, String content, Map<String, String> headers) {
		super(content == null ? new byte[0] : content.getBytes(),
				getHeaders(filename, headers), HttpStatus.OK);
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
