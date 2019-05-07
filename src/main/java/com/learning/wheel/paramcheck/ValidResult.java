package com.learning.wheel.paramcheck;

import java.io.Serializable;

/**
 * 参数校验返回结果result
 *
 * @author mazhenjie
 * @since 2019/5/7
 */
public class ValidResult implements Serializable {

    private static final long serialVersionUID = 4908491004980368729L;

    /**
     * 是否通过校验
     */
    private boolean success = false;

    /**
     * 未通过的参数名
     */
    private String paramName = "";

    /**
     * 未通过参数的报错信息(注解上的message)
     */
    private String paramError = "";

    private ValidResult() {
    }

    public static ValidResult success() {
        ValidResult r = new ValidResult();
        r.setSuccess(true);
        return r;
    }

    public static ValidResult fail(String paramName, String paramError) {
        ValidResult r = new ValidResult();
        r.setSuccess(false);
        r.setParamName(paramName);
        r.setParamError(paramError);
        return r;
    }

    /**
     * 校验是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return this.success;
    }

    private void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 获取未通过的参数名
     *
     * @return
     */
    public String getParamName() {
        return paramName;
    }

    private void setParamName(String paramName) {
        this.paramName = paramName;
    }

    /**
     * 未通过参数的报错信息
     *
     * @return
     */
    public String getParamError() {
        return paramError;
    }

    private void setParamError(String paramError) {
        this.paramError = paramError;
    }

    /**
     * 获取完整报错信息
     *
     * @return
     */
    public String getMsg() {
        return String.format("%s:%s", this.paramName, this.paramError);
    }
}
