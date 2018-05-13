/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import java.util.List;
import java.util.Objects;
import kp.ksl.compiler.types.KSLStruct.KSLStructField;

/**
 *
 * @author Asus
 */
public abstract class KSLType
{
    abstract String typeid();
    public abstract String getName();
    
    public abstract boolean isPrimitive();
    public abstract boolean isArray();
    public abstract boolean isStruct();
    public abstract boolean isReference();
    
    /* For Array */
    public abstract short getDimension();
    public abstract KSLType getBaseType();
    
    /* For Structs */
    public abstract boolean isValidField(String field);
    public abstract KSLStructField getField(String field);
    public abstract int getFieldCount();
    public abstract List<KSLStructField> getAllFields();
    
    /* Cast Operations */
    public abstract boolean canCastTo(KSLType type);
    
    /* Other */
    
    public final boolean equals(KSLType other) { return this == other || typeid().equals(other.typeid()); }
    
    public final boolean is(KSLType other) { return this == other || typeid().equals(other.typeid()); }
    public final boolean is(String typeid) { return typeid().equals(typeid); }
    
    @Override
    public final boolean equals(Object o) { return o instanceof KSLType && equals((KSLType) o); }

    @Override
    public final int hashCode()
    {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(typeid());
        return hash;
    }
    
    @Override
    public final String toString() { return getName(); }
}
