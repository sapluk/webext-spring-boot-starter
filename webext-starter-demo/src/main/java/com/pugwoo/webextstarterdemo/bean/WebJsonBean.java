package com.pugwoo.webextstarterdemo.bean;

import lombok.Data;

/**
 * 吐出到前端并序列化为json的bean。默认成功。
 * @date 2018-07-12
 * @author nick
 */
@Data
public class WebJsonBean {

    /** 返回码 */
    private int code;

    /** 返回码消息 */
    private String msg;

    /** 返回数据 */
    private Object data;

    /** 默认成功 */
    public WebJsonBean() {
        this.code = ErrorCode.SUCCESS.getCode();
        this.msg = ErrorCode.SUCCESS.getName();
    }

    /** 只给数据data，默认成功 */
    public WebJsonBean(Object data) {
        this.code = ErrorCode.SUCCESS.getCode();
        this.msg = ErrorCode.SUCCESS.getName();
        this.data = data;
    }

    /** 错误 */
    public WebJsonBean(IErrorCode code) {
        if (code == null) {
            this.code = -1;
            this.msg = "系统未知错误";
        } else {
            this.code = code.getCode();
            this.msg = code.getName();
        }
    }

    /**
     * 特别的，当code不是成功，且msg是null，且data是字符串时，设置msg的值为data的值，data设置为null
     */
    public WebJsonBean(IErrorCode code, Object data) {
        init(code, data, null);
    }

    /**
     * 特别的，当code不是成功，且msg是null，且data是字符串时，设置msg的值为data的值，data设置为null
     */
    public WebJsonBean(IErrorCode code, Object data, String msg) {
        init(code, data, msg);
    }

    /**
     * 特别的，当code不是成功，且msg是null，且data是字符串时，设置msg的值为data的值，data设置为null
     */
    private void init(IErrorCode code, Object data, String msg) {
        if (code == null) {
            this.code = -1;
            this.msg = "系统未知错误";
            this.data = data;
            return;
        }
        this.code = code.getCode();
        this.data = data;
        if (msg != null) {
            this.msg = msg;
        } else {
            if (code.getCode() != ErrorCode.SUCCESS.getCode() && data instanceof String) {
                this.msg = (String) data;
                this.data = null;
            } else {
                this.msg = code.getName();
            }
        }
    }
}
