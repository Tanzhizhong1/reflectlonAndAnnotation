package com.yc.junit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: reflectionAndAnnotation
 * @description:
 * @author: Joe
 * @create: 2021-03-31 19:49
 */
public class MyJunitRunner {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        //因为没用idea插件，只能先做 class加载
        Class cls=Class.forName("com.yc.MyCalculatorTest");
        //TODO: 升级：  按照maven约定的目录要求来扫描  test/java下的单员测试


        List<Method> Testlist=new ArrayList<>();

        Method beforeMethod=null;
        Method beforeClassMethod=null;
        Method afterMethod=null;
        Method afterClassMethod=null;

        //1.获取这个类中所以的方法

        Method [] ms=cls.getDeclaredMethods();
        //2.循环这些方法  判断上面加了哪些注解
        if(ms.length>0 && ms!=null){
            for(Method m:ms){
                //3.将这些方法分别存好  @Test对应的方法有多个，存到一个集合中  其他注解对应的方法只有一个，直接存
                if(m.isAnnotationPresent(MyTest.class)){
                    Testlist.add(m);
                }
                if(m.isAnnotationPresent(MyBefore.class)){
                    beforeMethod=m;
                }
                if(m.isAnnotationPresent(MyAfter.class)){
                    afterMethod=m;
                }
                if(m.isAnnotationPresent(MyBeforeClass.class)){
                    beforeClassMethod=m;
                }
                if(m.isAnnotationPresent(MyAfterClass.class)){
                    afterClassMethod=m;
                }
            }
        }

        //4.按junit运行的生命周期调用
        if(Testlist==null|| Testlist.isEmpty()){
            throw new RuntimeException("没有要测试的方法");
        }
        Object o=cls.newInstance();
        beforeClassMethod.invoke(o,null);
        for(Method m:Testlist){
            if(beforeMethod!=null){
                beforeMethod.invoke(o,null);
            }
            m.invoke(o,null);
            if(afterMethod!=null){
                afterMethod.invoke(o,null);
            }
        }
        afterClassMethod.invoke(o,null);
    }
}
