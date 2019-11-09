package com.imooc.o2o.util.stringTest;

public class StringDemo {
    public static void main(String[] args){
        String s1 = new String("hello");
        String s2 = new String("hello");
        System.out.println(s1 == s2);// false


        String s3 = "hello";
        String s4 = "he" + "llo";
        System.out.println(s3 == s4); // true

    }
}
