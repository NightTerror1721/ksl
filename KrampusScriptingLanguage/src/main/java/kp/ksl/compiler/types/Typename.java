/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import java.util.Collection;
import kp.ksl.compiler.types.KSLStruct.KSLStructField;

/**
 *
 * @author Asus
 */
final class Typename
{
    private Typename() {}
    
    public static final String VOID = "void";
    public static final String INTEGER = "int";
    public static final String FLOAT = "float";
    public static final String CHARACTER = "char";
    public static final String STRING = "string";
    public static final String REFERENCE = "ref";
    
    private static final String ARRAY_SYMBOL = "[]";
    
    public static final String integerName(Modifier mod)
    {
        return mod == null ? INTEGER : mod + " " + INTEGER;
    }
    
    public static final String floatName(Modifier mod)
    {
        if(mod == null)
            return FLOAT;
        if(mod.isUnsigned() || (!mod.isNormal() && !mod.isLong()))
            throw new IllegalArgumentException();
        return mod + " " + FLOAT;
    }
    
    public static final String arrayName(KSLType baseType, short dimension)
    {
        StringBuilder sb = new StringBuilder(baseType.getName());
        for(int i = 0; i < dimension; i++)
            sb.append(ARRAY_SYMBOL);
        return sb.toString();
    }
    
    public static String structName(Collection<KSLStructField> fields)
    {
        StringBuilder sb = new StringBuilder("struct {");
        if(!fields.isEmpty())
        {
            for(KSLStructField field : fields)
                sb.append(field.getType()).append(" ").append(field.getName()).append("; ");
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.append("}").toString();
    }
}
