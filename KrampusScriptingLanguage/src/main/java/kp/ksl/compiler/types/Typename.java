/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import java.util.HashMap;
import kp.ksl.lang.Struct;
import kp.ksl.lang.UnsignedByteInteger;
import kp.ksl.lang.UnsignedInteger;
import kp.ksl.lang.UnsignedShortInteger;

/**
 *
 * @author Asus
 */
public final class Typename
{
    private Typename() {}
    
    public static final String VOID = "void";
    public static final String INTEGER = "int";
    public static final String FLOAT = "float";
    public static final String BOOLEAN = "bool";
    public static final String CHARACTER = "char";
    public static final String STRING = "string";
    public static final String REFERENCE = "ref";
    
    public static final String integerName(TypeModifier mod)
    {
        return mod == null ? INTEGER : mod + " " + INTEGER;
    }
    
    public static final String floatName(TypeModifier mod)
    {
        if(mod == null)
            return FLOAT;
        if(mod.isUnsigned() || (!mod.isNormal() && !mod.isLong()))
            throw new IllegalArgumentException();
        return mod + " " + FLOAT;
    }
    
    public static final String arrayName(KSLType baseType, short dimension)
    {
        return arrayName(baseType.getName(), dimension);
    }
    public static final String arrayName(String name, short dimension)
    {
        if(dimension < 1)
            throw new IllegalArgumentException();
        char[] cstr = new char[name.length() + (dimension * 2)];
        for(int i=name.length();i<cstr.length;i+=2)
        {
            cstr[i] = '[';
            cstr[i + 1] = ']';
        }
        System.arraycopy(name.toCharArray(), 0, cstr, 0, name.length());
        return new String(cstr, 0, cstr.length);
    }
    public static final String arrayName(Class<?> javaClass)
    {
        if(!javaClass.isArray())
            throw new IllegalArgumentException();
        return of(javaClass.getComponentType()) + "[]";
    }
    
    public static final String structName(Class<?> javaClass) { return of(javaClass); }
    
    public static final String of(Class<?> javaClass)
    {
        String name = PRIMITIVE_TYPENAME.getOrDefault(javaClass, null);
        if(name != null)
            return name;
        if(javaClass.isArray())
            return of(javaClass.getComponentType()) + "[]";
        if(Struct.class.isAssignableFrom(javaClass))
            return "struct " + javaClass.getSimpleName();
        return "ref<" + javaClass.getName() + ">";
    }
    
    
    
    
    private static final HashMap<Class<?>, String> PRIMITIVE_TYPENAME = new HashMap<>();
    static {
        PRIMITIVE_TYPENAME.put(Void.TYPE, VOID);
        PRIMITIVE_TYPENAME.put(Byte.TYPE, integerName(TypeModifier.SIGNED_BYTE));
        PRIMITIVE_TYPENAME.put(Short.TYPE, integerName(TypeModifier.SIGNED_SHORT));
        PRIMITIVE_TYPENAME.put(Integer.TYPE, integerName(TypeModifier.SIGNED));
        PRIMITIVE_TYPENAME.put(Long.TYPE, integerName(TypeModifier.SIGNED_LONG));
        PRIMITIVE_TYPENAME.put(Byte.TYPE, integerName(TypeModifier.SIGNED_BYTE));
        PRIMITIVE_TYPENAME.put(UnsignedByteInteger.class, integerName(TypeModifier.UNSIGNED_BYTE));
        PRIMITIVE_TYPENAME.put(UnsignedShortInteger.class, integerName(TypeModifier.UNSIGNED_SHORT));
        PRIMITIVE_TYPENAME.put(UnsignedInteger.class, integerName(TypeModifier.UNSIGNED));
        PRIMITIVE_TYPENAME.put(Float.TYPE, floatName(TypeModifier.SIGNED));
        PRIMITIVE_TYPENAME.put(Double.TYPE, floatName(TypeModifier.SIGNED_LONG));
        PRIMITIVE_TYPENAME.put(Boolean.TYPE, BOOLEAN);
        PRIMITIVE_TYPENAME.put(Character.TYPE, CHARACTER);
        PRIMITIVE_TYPENAME.put(String.class, STRING);
        PRIMITIVE_TYPENAME.put(Object.class, REFERENCE);
    }
}
