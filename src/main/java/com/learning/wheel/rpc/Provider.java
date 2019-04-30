package com.learning.wheel.rpc;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSON;
import com.google.gson.JsonParseException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 封装的接口提供者的信息
 *
 * @author mazhenjie
 * @since 2019/4/26
 */
public class Provider {

    /**
     * 应用名，取不到默认为default-application
     */
    private String application;

    /**
     * 接口名
     */
    private String interfaceName;

    /**
     * 协议默认为dubbo协议
     */
    private String protocol = "dubbo://";

    /**
     * 服务ip+端口号
     */
    private String address;

    /**
     * token，因为存在无token的情况，所以不设置默认值
     */
    private String token;

    /**
     * 注册中心地址
     */
    private String registry;

    /**
     * 接口版本号
     * 不是dubbo版本号
     */
    private String version;

    /**
     * group
     */
    private String group;

    /**
     * 匹配接口上泛型的实例类型
     */
    private List<Type> genericInstanceList;

    public Provider() {}

    /**
     * 根据应用名，接口名，地址，注册中心初始化
     *
     * @param application
     * @param interfaceName
     * @param address
     * @param registry
     */
    public Provider(String application, String interfaceName, String address, String registry) {
        this.application = application;
        this.interfaceName = interfaceName;
        this.address = address;
        this.registry = registry;
    }

    /**
     * 泛化调用
     *
     * @param methodName
     * @param params
     * @return
     * @throws ClassNotFoundException
     */
    public String invoke(String methodName, List<String> params) throws ClassNotFoundException {
        if (StringUtils.isEmpty(methodName)) {
            throw new IllegalArgumentException("方法名必填");
        }

        //获取ReferenceConfig
        ReferenceConfig<GenericService> referenceConfig = getGenericReference();

        //该实例很重，里面封装了所有与注册中心及服务提供方连接，所以要缓存
        ReferenceConfigCache configCache = ReferenceConfigCache.getCache();
        GenericService genericService = configCache.get(referenceConfig);

        //反射获取接口class
        Class clazz = Class.forName(this.interfaceName);
        //遍历所有父接口匹配到指定方法的class
        Class classWithMethod = ClassUtil.getClassByMethod(clazz, methodName);
        if (classWithMethod == null) {
            throw new ClassNotFoundException("No Such Class with Method：" + methodName);
        }

        //获取匹配到方法的父接口泛型的实际类型
        Type[] superInterfaces = clazz.getGenericInterfaces();
        for (Type type : superInterfaces) {
            //接口名匹配并存在泛型
            if (type.getTypeName().startsWith(classWithMethod.getTypeName()) && type instanceof ParameterizedType) {
                ParameterizedType p = (ParameterizedType) type;
                genericInstanceList = new LinkedList<>(Arrays.asList(p.getActualTypeArguments()));
            }
        }

        //方法重载情况
        List<Method> methods = Stream.of(classWithMethod.getDeclaredMethods()).filter(method -> Objects.equals(method.getName(), methodName)).collect(Collectors.toList());
        for (Method method : methods) {
            if (method.getParameterCount() != params.size()) {
                continue;
            }

            try {
                String[] parameterTypeList = getParameterTypeList(method.getParameterTypes());

                boolean hasInstance = false;
                if (CollectionUtils.isNotEmpty(genericInstanceList)) {
                    hasInstance = true;
                }
                Object[] parameterValuesList = getParameterValuesList(method.getGenericParameterTypes(), hasInstance, params);

                //最终泛化调用
                Object result = genericService.$invoke(methodName, parameterTypeList, parameterValuesList);
                return JSON.toJSONString(result);
            } catch (NumberFormatException | JsonParseException e) {
                throw new RpcException(RpcException.UNKNOWN_EXCEPTION, "parse param error:" + e.toString());
            }
        }
        throw new RpcException(RpcException.UNKNOWN_EXCEPTION, "No Such Method");
    }

    /**
     * 根据根据provider信息构建ReferenceConfig
     *
     * @return
     */
    private ReferenceConfig<GenericService> getGenericReference() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(this.application);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(this.registry);

        ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setInterface(this.interfaceName);
        referenceConfig.setGeneric(true);
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setUrl(this.protocol + this.address);
        //超时时间设置10s
        referenceConfig.setTimeout(10000);
        if (StringUtils.isNotEmpty(this.group)) {
            referenceConfig.setGroup(this.group);
        }
        if (StringUtils.isNotEmpty(this.version)) {
            referenceConfig.setVersion(this.version);
        }
        if (StringUtils.isNotEmpty(this.token)) {
            Map<String, String> param = new ConcurrentHashMap<>(1);
            param.put("token", this.token);
            referenceConfig.setParameters(param);
        }
        return referenceConfig;
    }

    /**
     * 构建参数类型列表
     *
     * @param types
     * @return
     */
    private String[] getParameterTypeList(Class[] types) {
        String[] parameterTypeList = new String[types.length];
        //按顺序构造参数类型
        for (int i = 0; i < types.length; i++) {
            parameterTypeList[i] = types[i].getTypeName();
        }
        return parameterTypeList;
    }

    /**
     * 构建参数值列表
     *
     * @param genericTypes
     * @param hasInstance
     * @param params
     * @return
     */
    private Object[] getParameterValuesList(Type[] genericTypes, boolean hasInstance, List<String> params) {
        Object[] parameterValuesList = new Object[genericTypes.length];

        //按顺序构造参数值
        for (int i = 0; i < genericTypes.length; i++) {
            if (hasInstance && genericTypes[i] instanceof TypeVariable) {
                //TODO 目前是根据顺序匹配泛型的实例类型，期望直接通过某属性直接取到实例类型
                parameterValuesList[i] = ClassUtil.parseParameter(genericInstanceList.get(0), params.get(i));
                genericInstanceList.remove(0);
            } else {
                parameterValuesList[i] = ClassUtil.parseParameter(genericTypes[i], params.get(i));
            }
        }
        return parameterValuesList;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
