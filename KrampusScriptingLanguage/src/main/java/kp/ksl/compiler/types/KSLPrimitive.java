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
abstract class KSLPrimitive extends KSLType
{
    @Override
    public final boolean isPrimitive() { return true; }

    @Override
    public final boolean isArray() { return false; }

    @Override
    public final boolean isStruct() { return false; }

    @Override
    public final boolean isObject() { return false; }
    
    @Override
    public final short getDimension() { throw new UnsupportedOperationException(); }
    
    @Override
    public final KSLType getBaseType() { throw new UnsupportedOperationException(); }

    @Override
    public final boolean isValidField(String field) { throw new UnsupportedOperationException(); }
    
    @Override
    public final boolean canCastTo(KSLType type)
    {
        switch(type.typeid())
        {
            case Typeid.SINT8:
            case Typeid.SINT16:
            case Typeid.SINT32:
            case Typeid.SINT64:
            case Typeid.UINT8:
            case Typeid.UINT16:
            case Typeid.UINT32:
            case Typeid.FLOAT32:
            case Typeid.FLOAT64:
            case Typeid.CHAR:
                return true;
            default: return false;
        }
    }
}
