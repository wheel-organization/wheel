package com.learning.wheel.rpc;

import com.alibaba.dubbo.rpc.RpcException;

import java.util.List;

/**
 * dubbo泛化调用invoker
 * 调用入口
 *
 * @author mazhenjie
 * @since 2019/4/26
 */
public class DubboGenericInvoker {

    /**
     * 根据提供url泛化调用
     * url格式示例:dubbo://127.0.0.1:20880/com.xxx.DemoService?application=delivery-order-platform&default.token=souche_http_token&interface=com.xxx.DemoService&group=xxx
     * params顺序必须和方法的参数顺序相同，内容为序列化后的字符串
     *
     * @param url
     * @param methodName
     * @param params
     * @return
     * @throws ClassNotFoundException
     */
    public static String invoke(String url, String methodName, List<String> params) throws ClassNotFoundException {

        //获取所有注册中心下接口
        List<Provider> providerList = ProviderManager.getInstance().getProviders(url);

        for (Provider provider : providerList) {
            try {
                //泛化调用
                return provider.invoke(methodName, params);
            } catch (RpcException e) {
                //超时、网络、禁止访问异常继续调用
                if (e.isForbidded() || e.isTimeout() || e.isNetwork()) {
                    continue;
                }
                //其他异常直接抛出
                throw e;
            }
        }
        throw new RpcException(404, "no provider successfully invoked");
    }
}