package com.yhy.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestReflect {
    public void method1(){
        System.out.println("test");
    }
}

class TestDemo{
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Class<?> cls = Class.forName("TestReflect");
        Object o = cls.newInstance();
        Method method1 = cls.getMethod("method1");
        method1.invoke(o);
    }

}
