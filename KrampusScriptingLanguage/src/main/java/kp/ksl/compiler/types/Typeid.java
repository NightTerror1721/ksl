/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

/**
 *
 * @author Asus
 */
final class Typeid
{
    private Typeid() {}
    
    public static final char PREFIX_SINT = 'i';
    public static final char PREFIX_UINT = 'u';
    public static final char PREFIX_FLOAT = 'f';
    public static final char PREFIX_ARRAY = 'a';
    public static final char PREFIX_STRUCT = 's';
    
    public static final char SUFIX_1BYTE = 'b';
    public static final char SUFIX_2BYTE = 's';
    public static final char SUFIX_4BYTE = 'i';
    public static final char SUFIX_8BYTE = 'l';
    public static final char SUFIX_END = ';';
    
    public static final String SINT8 = "ib";
    public static final String SINT16 = "is";
    public static final String SINT32 = "ii";
    public static final String SINT64 = "il";
    
    public static final String UINT8 = "ub";
    public static final String UINT16 = "us";
    public static final String UINT32 = "ui";
    
    public static final String FLOAT32 = "fi";
    public static final String FLOAT64 = "fl";
    
    public static final String CHAR = "c";
    
    public static final String REFERENCE = "r";
    
    public static final boolean isNumeric(String typeid)
    {
        if(typeid.length() == 2)
        {
            char c = typeid.charAt(0);
            return c == PREFIX_SINT || c == PREFIX_UINT || c == PREFIX_FLOAT;
        }
        return false;
    }
    
    public static final boolean isInteger(String typeid)
    {
        if(typeid.length() == 2)
        {
            char c = typeid.charAt(0);
            return c == PREFIX_SINT || c == PREFIX_UINT;
        }
        return false;
    }
    public static final boolean isSignedInteger(String typeid) { return typeid.length() == 2 && typeid.charAt(0) == PREFIX_SINT; }
    public static final boolean isUnsignedInteger(String typeid) { return typeid.length() == 2 && typeid.charAt(0) == PREFIX_UINT; }
    
    public static final boolean isFloat(String typeid) { return typeid.length() == 2 && typeid.charAt(0) == PREFIX_FLOAT; }
    
    public static final String generateArrayTypeid(KSLType baseType, short depth)
    {
        if(depth < 1)
            throw new IllegalArgumentException();
        return PREFIX_ARRAY + baseType.typeid() + depth + SUFIX_END;
    }
    
    public static final String generateStructTypeid(KSLType... types)
    {
        if(types == null)
            throw new NullPointerException();
        if(types.length < 1)
            throw new IllegalArgumentException();
        StringBuilder sb = new StringBuilder(16);
        sb.append(PREFIX_STRUCT);
        for(KSLType type : types)
            sb.append(type.typeid());
        return sb.append(SUFIX_END).toString();
    }
}
