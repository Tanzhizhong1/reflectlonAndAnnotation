package com.yc.junit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: reflectionAndAnnotation
 * @description:
 * @author: Joe
 * @create: 2021-03-31 19:49
 */
public class MyJunitRunner1 {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        //因为没用idea插件，只能先做 class加载
        Class cls=Class.forName("com.yc.MyCalculatorTest");
        List<Method> Testlist=new ArrayList<>();
        Map<String ,Method> map=new HashMap<>();

        //1.获取这个类中所以的方法

        Method [] ms=cls.getDeclaredMethods();
        //2.循环这些方法  判断上面加了哪些注解
        if(ms.length>0 && ms!=null){
            for(Method m:ms){
//                Annotation[] as=m.getAnnotations();
//                for(Annotation a:as){
//                    System.out.println(a.annotationType());
//                }
//                System.out.println("====");
//                System.out.println(m.getAnnotations()[0]);
                if(m.getAnnotations()[0].toString().equals("@com.yc.junit.MyTest()")){
                    Testlist.add(m);
                }else{
                    map.put(m.getAnnotations()[0].toString(),m);
                }
            }
        }
        System.out.println(Testlist +"===" +map);

        //3.将这些方法分别存好  @Test对应的方法有多个，存到一个集合中  其他注解对应的方法只有一个，直接存


        //4.按junit运行的生命周期调用
        Object o=cls.newInstance();
        map.get("@com.yc.junit.MyBeforeClass()").invoke(o);
        for(Method m:Testlist){
            map.get("@com.yc.junit.MyBefore()").invoke(o);
            m.invoke(o);
            map.get("@com.yc.junit.MyAfter()").invoke(o);
        }
        map.get("@com.yc.junit.MyAfterClass()").invoke(o);
    }
}
