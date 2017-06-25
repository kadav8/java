package com.mycompany.eeapp;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Qualifier
@Retention(RUNTIME)
@Documented
public @interface Config {

    @Nonbinding
    String key() default "";

    @Nonbinding
    String defaultValue() default "";

}
