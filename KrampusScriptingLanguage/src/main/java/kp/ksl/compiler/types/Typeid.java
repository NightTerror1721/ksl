/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import java.util.HashMap;

/**
 *
 * @author Asus
 */
public final class Typeid
{
    private Typeid() {}
    
    public static final char PREFIX_VOID = 'v';
    public static final char PREFIX_SINT = 'i';
    public static final char PREFIX_UINT = 'u';
    public static final char PREFIX_FLOAT = 'f';
    public static final char PREFIX_CHAR = 'c';
    public static final char PREFIX_ARRAY = 'a';
    public static final char PREFIX_STRUCT = 's';
    
    public static final char SUFIX_1BYTE = 'b';
    public static final char SUFIX_2BYTE = 's';
    public static final char SUFIX_4BYTE = 'i';
    public static final char SUFIX_8BYTE = 'l';
    public static final char SUFIX_END = ';';
    
    public static final String VOID = "V";
    
    public static final String SINT8 = "B";
    public static final String SINT16 = "S";
    public static final String SINT32 = "I";
    public static final String SINT64 = "J";
    
    public static final String UINT8 = "Lkp.ksl.lang.UnsignedByteInteger;";
    public static final String UINT16 = "Lkp.ksl.lang.UnsignedShortInteger;";
    public static final String UINT32 = "Lkp.ksl.lang.UnsignedInteger;";
    
    public static final String FLOAT32 = "F";
    public static final String FLOAT64 = "D";
    
    public static final String BOOLEAN = "Z";
    
    public static final String CHARACTER = "C";
    public static final String STRING = "Ljava.lang.String;";
    
    public static final String REFERENCE = "Ljava.lang.Object;";
    
    public static final boolean isVoid(String typeid) { return typeid.equals(VOID); }
    
    public static final boolean isNumeric(String typeid)
    {
        switch(typeid)
        {
            case SINT8: case SINT16: case SINT32: case SINT64:
            case UINT8: case UINT16: case UINT32:
            case FLOAT32: case FLOAT64:
                return true;
            default: return false;
        }
    }
    
    public static final boolean isInteger(String typeid)
    {
        switch(typeid)
        {
            case SINT8: case SINT16: case SINT32: case SINT64:
            case UINT8: case UINT16: case UINT32:
                return true;
            default: return false;
        }
    }
    public static final boolean isSignedInteger(String typeid)
    {
        switch(typeid)
        {
            case SINT8: case SINT16: case SINT32: case SINT64:
                return true;
            default: return false;
        }
    }
    public static final boolean isUnsignedInteger(String typeid)
    {
        switch(typeid)
        {
            case UINT8: case UINT16: case UINT32:
                return true;
            default: return false;
        }
    }
    
    public static final boolean isFloat(String typeid)
    {
        switch(typeid)
        {
            case FLOAT32: case FLOAT64:
                return true;
            default: return false;
        }
    }
    
    public static final boolean isCharacter(String typeid) { return typeid.equals(CHARACTER); }
    public static final boolean isString(String typeid) { return typeid.equals(STRING); }
    
    public static final String arrayTypeid(Class<?> javaClass, short dimension)
    {
        return Typeid.arrayTypeid(javaClass.getName(), dimension);
    }
    public static final String arrayTypeid(KSLType baseType, short dimension)
    {
        return Typeid.arrayTypeid(baseType.typeid(), dimension);
    }
    public static final String arrayTypeid(String typeid, short dimension)
    {
        if(dimension < 1)
            throw new IllegalArgumentException();
        char[] cstr = new char[typeid.length() + dimension];
        for(int i=0;i<dimension;i++)
            cstr[i] = '[';
        System.arraycopy(typeid.toCharArray(), 0, cstr, dimension, typeid.length());
        return new String(cstr, 0, cstr.length);
    }
    
    public static final String structTypeid(Class<?> javaClass) { return of(javaClass); }
    
    /*public static final String structId(Collection<KSLStructField> types)
    {
        if(types == null)
            throw new NullPointerException();
        if(types.isEmpty())
            throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder(16);
        sb.append(PREFIX_STRUCT);
        for(KSLStructField type : types)
            sb.append(type.getType().typeid());
        return sb.append(SUFIX_END).toString();
    }*/
    
    
    
    
    
    /* Typeid Conversions */
    private static final HashMap<Class<?>, String> PRIMITIVE_TYPEID = new HashMap<>();
    static {
        PRIMITIVE_TYPEID.put(Void.TYPE, VOID);
        PRIMITIVE_TYPEID.put(Byte.TYPE, SINT8);
        PRIMITIVE_TYPEID.put(Short.TYPE, SINT16);
        PRIMITIVE_TYPEID.put(Integer.TYPE, SINT32);
        PRIMITIVE_TYPEID.put(Long.TYPE, SINT64);
        PRIMITIVE_TYPEID.put(Float.TYPE, FLOAT32);
        PRIMITIVE_TYPEID.put(Double.TYPE, FLOAT64);
        PRIMITIVE_TYPEID.put(Character.TYPE, CHARACTER);
        PRIMITIVE_TYPEID.put(Boolean.TYPE, BOOLEAN);
    }
    
    public static final String of(Class<?> javaClass)
    {
        String typeid = PRIMITIVE_TYPEID.getOrDefault(javaClass, null);
        return typeid != null ? typeid : javaClass.getName();
    }
}
