package com.learning.wheel.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Properties;

/**
 * 配置文件util
 *
 * @author mazhenjie
 * @since 2019/4/30
 */
public class PropertyUtil {
    /**
     * 根据路径读取配置
     *
     * @param path
     * @return
     */
    public static Properties getConfig(String path) {
        Properties props = null;
        try {
            props = new Properties();
            InputStream in = PropertyUtil.class.getResourceAsStream(path);
            BufferedReader bf = new BufferedReader(new InputStreamReader(in));
            props.load(bf);
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return props;
    }

    /**
     * 根据key读取默认配置路径下的value
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        Properties prop = getConfig("classpath*:**/*.properties");
        if (Objects.isNull(prop)) {
            return null;
        }

        return prop.getProperty(key);
    }
}