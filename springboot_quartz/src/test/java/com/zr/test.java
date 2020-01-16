package com.zr;

public class test {
    public static void main(String[] args) {

        int a = 40;
        Integer b = 40;
        Integer c = new Integer(40);

        System.out.println(a == b);//true
        System.out.println(a == c);//true
        System.out.println(b == c);//false

        int d = -400;
        Integer e = -400;
        Integer f = new Integer(-400);

        System.out.println(d == e);  //true
        System.out.println(d == f);  //true
        System.out.println(f == e);  // false

    }

}
