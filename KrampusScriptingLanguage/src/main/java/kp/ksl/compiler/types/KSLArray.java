/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import org.apache.bcel.generic.ArrayType;

/**
 *
 * @author Asus
 */
public final class KSLArray extends KSLType
{   
    private final KSLType base;
    private final short dimension;
    
    private KSLArray(KSLType base, short dim)
    {
        super(Typeid.arrayTypeid(base, dim), Typename.arrayName(base, dim), new ArrayType(base.getJavaType(), dim),
                findArrayJavaClass(base.getJavaClass(), dim));
        this.base = base;
        this.dimension = dim;
    }
    
    public static final KSLArray createArrayType(KSLType baseType, short dimension)
    {
        if(baseType == null)
            throw new NullPointerException();
        if(dimension < 1)
            throw new IllegalArgumentException();
        if(baseType.isArray())
        {
            dimension = (short) (baseType.getDimension() + dimension);
            if(dimension < 1)
                throw new IllegalArgumentException();
            return new KSLArray(baseType.getBaseType(), dimension);
        }
        return new KSLArray(baseType, dimension);
    }
    
    @Override
    public final boolean isMutable() { return true; }

    @Override
    public final boolean isArray() { return true; }
    
    @Override
    public final short getDimension() { return dimension; }
    
    @Override
    public final KSLType getBaseType() { return base; }
    
    private static Class<?> findArrayJavaClass(Class<?> base, short dim)
    {
        try { return Class.forName(Typeid.arrayTypeid(base.getName(), dim)); }
        catch(ClassNotFoundException ex) { throw new IllegalStateException(ex); }
    }

    @Override
    public final boolean isManualAssignableFrom(KSLType type)
    {
        return isAutoAssignableFrom(type) || type.getJavaClass().isAssignableFrom(jclass);
    }

    @Override
    public final boolean isAutoAssignableFrom(KSLType type)
    {
        return jclass.isAssignableFrom(type.getJavaClass());
    }
}
