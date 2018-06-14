/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.meta;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface KSLScript
{
    public static final String DEFAULT_CACHE_FIELD_NAME = "__metaclass";
    public static final String INVALID_MACRO_REPOSITORY = "";
    public static final String INVALID_STRUCT_REPOSITORY = "";
    
    String instanceName();
    String macrosRepository() default INVALID_MACRO_REPOSITORY;
    String structsRepository() default INVALID_STRUCT_REPOSITORY;
    String cacheName() default DEFAULT_CACHE_FIELD_NAME;
}
