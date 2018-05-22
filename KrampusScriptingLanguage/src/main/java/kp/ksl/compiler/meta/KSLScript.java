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
    
    String instanceName();
    String cacheName() default DEFAULT_CACHE_FIELD_NAME;
}
