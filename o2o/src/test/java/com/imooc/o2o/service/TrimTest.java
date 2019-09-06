package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import org.junit.Test;

public class TrimTest extends BaseTest {

    /**
     * trim()函数移除字符串两侧的空白字符或其他预定义字符。
     *
     * 功能除去字符串开头和末尾的空格或其他字符。
     * 函数执行成功时返回删除了string字符串首部和尾部空格的字符串，发生错误时返回空字符串（""）。如果任何参数的值为NULL,Trim() 函数返回NULL。
     */
    @Test
    public void trimTest(){

        String result = "  66  ";

        System.out.println(result);

        // .trim() 去除前后空格；
        result = result.trim();

        System.out.println(result);

    }

}
