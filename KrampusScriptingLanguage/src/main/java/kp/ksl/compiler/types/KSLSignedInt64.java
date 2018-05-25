/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import org.apache.bcel.generic.BasicType;

/**
 *
 * @author Asus
 */
public final class KSLSignedInt64 extends KSLPrimitive
{
    public KSLSignedInt64() { super(Typeid.SINT64, Typename.integerName(TypeModifier.SIGNED_LONG), BasicType.LONG, Long.TYPE, Long.class); }
    
    @Override
    public final boolean requireDoubleStackEntry() { return true; }
    
    @Override
    public final boolean isAutoAssignableFrom(KSLType type)
    {
        if(is(type))
            return true;
        switch(type.typeid())
        {
            case Typeid.BOOLEAN:
            case Typeid.SINT8:
            case Typeid.UINT8:
            case Typeid.SINT16:
            case Typeid.UINT16:
            case Typeid.CHARACTER:
            case Typeid.SINT32:
            case Typeid.UINT32:
                return true;
            default: return false;
        }
    }
}
