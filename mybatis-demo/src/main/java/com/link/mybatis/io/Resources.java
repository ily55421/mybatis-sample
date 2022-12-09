package com.link.mybatis.io;

import java.io.InputStream;

/**
 * @author lin 2022/12/7 18:35
 * 资源加载
 */
public class Resources {
    private Resources() {
    }

    public static InputStream getResourceAsStream(String file) {
        //从classpath中读取文件
        return Resources.class.getClassLoader().getResourceAsStream(file);
    }
}
