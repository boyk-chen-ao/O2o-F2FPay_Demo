package com.imooc.o2o.util.stringTest;

public class StringDeno {
    public static void main(String[] args) {
        String s1 = "hello";
        String s2 = "world";
        String s3 = "helloworld";
        System.out.println(s3 == s1 + s2);// false   s1+s2先开空间再拼接。
        System.out.println(s3.equals((s1 + s2)));// true

        System.out.println(s3 == "hello" + "world");// true
        System.out.println(s3.equals("hello" + "world"));// true

        System.out.println(s3 == new String("hello" + "world"));// false
        System.out.println(s3.equals(new String("hello" + "world")));// true

        // 通过反编译看源码，我们知道这里已经做好了处理。
        // System.out.println(s3 == "helloworld");
        // System.out.println(s3.equals("helloworld"));


        StringBuffer a = new StringBuffer("hello");
        a.append("world");
        System.out.println(a);
        System.out.println(a.toString() == "hello" + "world");// false
        System.out.println(a.toString().equals("hello" + "world"));// true

        System.out.println(a.toString() == new String("hello" + "world"));// false


    }
}
