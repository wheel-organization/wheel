package com.learning.wheel.rpc;

import com.alibaba.dubbo.common.URL;
import com.learning.wheel.utils.PropertyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * ProviderManager
 *
 * @author mazhenjie
 * @since 2019/4/26
 */
public class ProviderManager {

    private static ProviderManager instance = new ProviderManager();

    private ProviderManager() {}

    public static ProviderManager getInstance() {
        return instance;
    }

    /**
     * 换成自己项目读取配置文件的方式
     */
    private String zkUrl = PropertyUtil.getValue("dubbo.registry");

    /**
     * 获取所有提供者
     *
     * @param url
     * @return
     */
    public List<Provider> getProviders(String url) {
        //解析url
        URL u = URL.valueOf(url);
        //获取所有注册中心地址
        String[] zkUrls = zkUrl.split(";");
        List<Provider> providerList = new ArrayList<>();
        for (String zk : zkUrls) {
            //build provider
            Provider provider = new Provider(u.getParameter("application", "default-application"), u.getPath(), u.getAddress(), zk);
            provider.setToken(u.getParameter("default.token"));
            provider.setGroup(u.getParameter("group"));
            provider.setVersion(u.getParameter("version"));

            providerList.add(provider);
        }
        return providerList;
    }
}
