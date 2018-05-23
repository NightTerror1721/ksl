/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import org.apache.bcel.generic.Type;

/**
 *
 * @author Asus
 */
abstract class KSLPrimitive extends KSLType
{
    KSLPrimitive(String id, String name, Type javaType, Class<?> javaClass) { super(id, name, javaType, javaClass); }
    
    @Override
    public final boolean isMutable() { return false; }
    
    @Override
    public final boolean isPrimitive() { return true; }
    
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
            case Typeid.BOOLEAN:
            case Typeid.CHARACTER:
                return true;
            default: return false;
        }
    }
}
