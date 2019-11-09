package com.imooc.o2o.util.stringTest;

public class NumberA {

    public static void main(String[] args){

        // 定义一个字符串
        String s = "aljlkdsflkjsadjfklhasdkjlflkajdflwoiudsafhaasdasd";

        String[] arr = s.split("a");

        int a = arr.length;

        for (int i=0 ; i<9; i++){
            System.out.println(arr[i]);

        }

        System.out.println(a-1);
        System.out.println("arr0:"+arr[0]);


        // 创建一个StringBuilder对象，用来存储字符串
        StringBuilder hobby=new StringBuilder("爱慕课");

        System.out.println(hobby);


        // 创建一个空的StringBuilder对象
        StringBuilder str = new StringBuilder();

        // 追加字符串
        str.append("jaewkjldfxmopzdm");

        // 从后往前每隔三位插入逗号
        int i=str.length();
        while(i>3){
            i=i-3;
            str.insert(i,",");
        }

        // 将StringBuilder对象转换为String对象并输出
        System.out.print(str.toString());
    }
}
