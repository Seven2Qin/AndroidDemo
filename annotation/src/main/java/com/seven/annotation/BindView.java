package com.seven.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

@Retention(RetentionPolicy.RUNTIME)
//@Retention(CLASS)
@Target(FIELD)
public @interface BindView {
    int value();
}