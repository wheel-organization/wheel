package com.learning.wheel.beans;

/**
 * @author hangwei
 * @date 2019/5/14 下午 3:07
 */
public interface IMethodCallBack {

    String getMethodName();

    ToBean callMethod(FromBean frombean)  throws Exception;

}
