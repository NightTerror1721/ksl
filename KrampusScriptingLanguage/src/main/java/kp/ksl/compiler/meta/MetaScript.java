/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.meta;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 *
 * @author Asus
 */
public class MetaScript<C> extends MetaClass<C>
{
    private final HashMap<String, Variable> fields = new HashMap<>();
    private final HashMap<String, Function> funcs = new HashMap<>();
    
    private MetaScript(Class<C> jclass) { super(jclass); }
    
    private static boolean isInstancePresent(Class<?> jclass, KSLScript a)
    {
        try
        {
            Field field = jclass.getField(a.instanceName());
            if(field == null)
                return false;
            if(field.getType() != jclass)
                return false;
            if(!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers()) ||
                    !Modifier.isPublic(field.getModifiers()))
                return false;
            return field.get(null) != null;
        }
        catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) { return false; }
    }
    
    private static MetaScript findMetaScriptCache(Class<?> jclass, KSLScript a)
    {
        try
        {
            Field field = jclass.getField(a.cacheName());
            if(field == null)
                return null;
            if(field.getType() != MetaScript.class)
                return null;
            if(!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers()) ||
                    !Modifier.isPublic(field.getModifiers()))
                return null;
            return (MetaScript) field.get(null);
        }
        catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) { return null; }
    }
    
    private static void setMetaScriptCache(Class<?> jclass, KSLScript a, MetaScript s)
    {
        try
        {
            Field field = jclass.getField(a.cacheName());
            if(field == null)
                return;
            if(field.getType() != MetaScript.class)
                return;
            if(!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers()) ||
                    !Modifier.isPublic(field.getModifiers()))
                return;
            field.set(null, s);
        }
        catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {}
    }
    
    private static void findVariables(Class<?> jclass, MetaScript s)
    {
        for(Field field : jclass.getFields())
        {
            if(Modifier.isStatic(field.getModifiers()))
        }
    }
    
    private static void findFunctions(Class<?> jclass, MetaScript s)
    {
        
    }
    
    public static final <C> MetaScript<C> createMetaScript(Class<C> jclass)
    {
        if(!jclass.isAnnotationPresent(KSLScript.class))
            return null;
        KSLScript a = jclass.getAnnotation(KSLScript.class);
        if(!isInstancePresent(jclass, a))
            return null;
        
        MetaScript<C> s = findMetaScriptCache(jclass, a);
        if(s != null)
            return s;
        
        s = new MetaScript(jclass);
        findVariables(jclass, s);
        findFunctions(jclass, s);
        setMetaScriptCache(jclass, a, s);
        
        return s;
    }

    @Override
    public final boolean isScript() { return true; }

    @Override
    public final boolean isReference() { return false; }
}
