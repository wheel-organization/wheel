package com.learning.wheel.rpc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * 内部用ClassUtil
 *
 * @author mazhenjie
 * @since 2019/4/25
 */
public class ClassUtil {

    /**
     * 根据参数type解析value
     *
     * @param type
     * @param arg
     * @return
     */
    public static Object parseParameter(Type type, String arg) {
        String className = type.getTypeName();

        if (Objects.equals(className, "int")) {
            return Integer.parseInt(arg);
        } else if (Objects.equals(className, "double")) {
            return Double.parseDouble(arg);
        } else if (Objects.equals(className, "short")) {
            return Short.parseShort(arg);
        } else if (Objects.equals(className, "float")) {
            return Float.parseFloat(arg);
        } else if (Objects.equals(className, "long")) {
            return Long.parseLong(arg);
        } else if (Objects.equals(className, "byte")) {
            return Byte.parseByte(arg);
        } else if (Objects.equals(className, "boolean")) {
            return Boolean.parseBoolean(arg);
        } else if (Objects.equals(className, "char")) {
            return arg.charAt(0);
        } else if (Objects.equals(className, "java.lang.String")
                || Objects.equals(className, "String") || Objects.equals(className, "string")) {
            return String.valueOf(arg);
        } else if (Objects.equals(className, "java.lang.Integer")
                || Objects.equals(className, "Integer") || Objects.equals(className, "integer")) {
            return Integer.valueOf(arg);
        } else if (Objects.equals(className, "java.lang.Double")
                || Objects.equals(className, "Double")) {
            return Double.valueOf(arg);
        } else if (Objects.equals(className, "java.lang.Short")
                || Objects.equals(className, "Short")) {
            return Short.valueOf(arg);
        } else if (Objects.equals(className, "java.lang.Long")
                || Objects.equals(className, "Long")) {
            return Long.valueOf(arg);
        } else if (Objects.equals(className, "java.lang.Float")
                || Objects.equals(className, "Float")) {
            return Float.valueOf(arg);
        } else if (Objects.equals(className, "java.lang.Byte")
                || Objects.equals(className, "Byte")) {
            return Byte.valueOf(arg);
        } else if (Objects.equals(className, "java.lang.Boolean")
                || Objects.equals(className, "Boolean")) {
            return Boolean.valueOf(arg);
        } else {
            GsonBuilder builder = new GsonBuilder();
            //添加解析器，解决date无法序列化问题
            builder.registerTypeAdapter(Date.class,
                    (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()));
            Gson gson = builder.create();

            return gson.fromJson(arg, type);
        }
    }

    /**
     * 根据方法名找到class，没找到递归调用父接口
     * 没有找到返回null
     *
     * @param clazz
     * @param methodName
     * @return
     */
    public static Class getClassByMethod(Class clazz, String methodName) {
        if (Stream.of(clazz.getDeclaredMethods()).anyMatch(method -> Objects.equals(methodName, method.getName()))) {
            return clazz;
        }
        for (Class c : clazz.getInterfaces()) {
            Class result = getClassByMethod(c, methodName);
            if (result != null) {
                return result;
            }
            continue;
        }
        return null;
    }
}
