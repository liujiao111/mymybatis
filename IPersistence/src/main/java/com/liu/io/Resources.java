package com.liu.io;

import java.io.InputStream;

/**
 * 读取文件内容为字节流
 */
public class Resources {
    public static InputStream getResourceAsStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
