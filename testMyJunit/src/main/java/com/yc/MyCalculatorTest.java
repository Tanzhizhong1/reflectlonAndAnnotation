package com.yc;

import com.yc.biz.Calculator;
import com.yc.junit.*;


public class MyCalculatorTest {

    private Calculator cal;

    @MyBeforeClass
    public static void bs(){
        System.out.println("beforeClass");
    }

    @MyBefore
    public void setUp() throws Exception {
        System.out.println(" before ");
        cal=new Calculator();

    }

    @MyAfter
    public void tearDown() throws Exception {
        System.out.println(" after ");
    }


    @MyAfterClass
    public static void as(){
        System.out.println(" afterClass ");
    }

    @MyTest
    public void add() {
        System.out.println(" add测试 ");
    }

    @MyTest
    public void sub() {
        System.out.println(" sub测试 ");
    }
}