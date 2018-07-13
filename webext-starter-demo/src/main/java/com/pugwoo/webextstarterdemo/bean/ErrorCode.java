package com.pugwoo.webextstarterdemo.bean;

/**
 * 错误代码: <br/>
 *          <0  系统错误<br/>
 *          =0  成功<br/>
 *          >0  业务错误<br/>
 *       >1000  为分段的业务错误代码<br/>
 *
 *  说明：使用方自行维护自己的ErrorCode，只要实现IErrorCode接口即可<br/>
 * @date 2018-07-12
 */
public enum ErrorCode implements IErrorCode {

//---------------- < 0 系统错误 ----------------
    /** 响应状态: 系统未知错误 */
    SYSTEM_ERROR(-1, "系统未知错误"),

    /** 响应状态: 数据库异常 */
    DB_ERROR(-2, "数据库异常"),

    /** 响应状态: SOA异常 */
    SOA_ERROR(-3, "SOA异常"),

//------------------ 0 成功 --------------------
    /** 响应状态: 成功 */
    SUCCESS(0, "成功"),

//---------------- > 0 业务错误 ----------------
    /** 响应状态: 未登录 */
    NOT_LOGIN(1, "未登录"),

    /** 响应状态: 缺少参数 */
    MISSING_PARAMETERS(2,"缺少参数"),

    /** 响应状态: 参数错误 */
    ILLEGAL_PARAMETERS(3,"参数错误"),

    /**
     * 响应状态: 一般业务异常<br/>
     *  · 该异常需带上错误说明<br/>
     */
    COMMON_BIZ_ERROR(4, "一般业务异常"),

    /** 响应状态: 验证码错误或已失效 */
    WRONG_CAPTCHA(5, "验证码错误或已失效"),

    /** 响应状态: 没有权限 */
    PERMISSION_DENIED(6, "没有权限"),

    /** 响应状态: 超过限额 */
    EXCEED_LIMIT(7, "超过限额"),

    /** 响应状态: 没有配置上传存储服务 */
    NO_UPLOAD_PROVIDER(8, "没有配置上传存储服务"),

//------ 1000 以上为分段的业务错误代码 ------------

    ;

    /** 错误代码 */
    private int code;

    /** 错误代码名称 */
    private String name;

    ErrorCode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     *  通过错误代码获取 ErrorCode
     * @param code 错误代码
     * @return 错误代码对应的ErrorCode
     */
    public static ErrorCode getByCode(int code) {
        for(ErrorCode e : ErrorCode.values()) {
            if(code == e.getCode()) {
                return e;
            }
        }
        return null;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

}
