package com.seven.javademo;

public class ParentClass {
    public String parentName = "ParentClass";
    public static String getParentName() {
        return ParentClass.class.getSimpleName();
    }
}
