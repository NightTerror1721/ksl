/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import java.util.List;
import kp.ksl.compiler.types.KSLStruct.KSLStructField;

/**
 *
 * @author Asus
 */
public final class KSLArray extends KSLType
{
    private static final String ARRAY_SYMBOL = "[]";
    
    private final String typeid;
    private final String name;
    private final KSLType base;
    private final short dimension;
    
    private KSLArray(KSLType base, short dim)
    {
        this.base = base;
        this.dimension = dim;
        this.typeid = Typeid.generateArrayTypeid(base, dim);
        this.name = generateName();
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
    
    private String generateName()
    {
        StringBuilder sb = new StringBuilder(base.getName());
        for(int i = 0; i < dimension; i++)
            sb.append(ARRAY_SYMBOL);
        return sb.toString();
    }
    
    @Override
    final String typeid() { return typeid; }

    @Override
    public final String getName() { return name; }

    @Override
    public final boolean isPrimitive() { return false; }

    @Override
    public final boolean isArray() { return true; }

    @Override
    public final boolean isStruct() { return false; }

    @Override
    public final boolean isReference() { return false; }
    
    @Override
    public final short getDimension() { return dimension; }
    
    @Override
    public final KSLType getBaseType() { return base; }
    
    @Override
    public final boolean isValidField(String field) { throw new UnsupportedOperationException(); }
    
    @Override
    public final KSLStruct.KSLStructField getField(String field) { throw new UnsupportedOperationException(); }
    
    @Override
    public final int getFieldCount() { throw new UnsupportedOperationException(); }
    
    @Override
    public final List<KSLStructField> getAllFields() { throw new UnsupportedOperationException(); }
    
    @Override
    public final boolean canCastTo(KSLType type) { return is(type); }
}
