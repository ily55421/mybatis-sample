package com.link.mybatis.io;

import java.io.InputStream;

/**
 * @author lin 2022/12/9 20:58
 * 资源读取类
 */
public class Resources {
    public static InputStream getResourceAsStream(String file) {
        //从classpath中读取文件
        return Resources.class.getClassLoader().getResourceAsStream(file);
    }
}
