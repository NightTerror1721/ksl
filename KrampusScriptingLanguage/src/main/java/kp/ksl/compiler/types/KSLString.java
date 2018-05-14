/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import java.util.List;
import kp.ksl.compiler.types.KSLStruct.KSLStructField;
import org.apache.bcel.generic.BasicType;

/**
 *
 * @author Asus
 */
public final class KSLString extends KSLType
{
    public KSLString() { super(Typeid.STRING, Typename.STRING, BasicType.STRING); }
    
    @Override
    public final boolean isMutable() { return false; }

    @Override
    public final boolean isPrimitive() { return false; }
    
    @Override
    public final boolean isString() { return true; }

    @Override
    public final boolean isArray() { return false; }

    @Override
    public final boolean isStruct() { return false; }

    @Override
    public final boolean isReference() { return false; }

    @Override
    public final short getDimension() { throw new UnsupportedOperationException(); }

    @Override
    public final KSLType getBaseType() { throw new UnsupportedOperationException(); }

    @Override
    public final boolean isValidField(String field) { throw new UnsupportedOperationException(); }

    @Override
    public final KSLStructField getField(String field) { throw new UnsupportedOperationException(); }

    @Override
    public final int getFieldCount() { throw new UnsupportedOperationException(); }

    @Override
    public final List<KSLStructField> getAllFields() { throw new UnsupportedOperationException(); }

    @Override
    public final boolean canCastTo(KSLType type) { throw new UnsupportedOperationException(); }
    
}
