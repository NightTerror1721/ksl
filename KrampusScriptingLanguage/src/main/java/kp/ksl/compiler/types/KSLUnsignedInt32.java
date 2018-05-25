/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import kp.ksl.lang.UnsignedInteger;
import org.apache.bcel.generic.ObjectType;

/**
 *
 * @author Asus
 */
public final class KSLUnsignedInt32 extends KSLPrimitive
{
    public KSLUnsignedInt32() { super(Typeid.UINT32,
            Typename.integerName(TypeModifier.UNSIGNED),
            new ObjectType(UnsignedInteger.class.getName()),
            UnsignedInteger.class,
            null); }
    
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
                return true;
            default: return false;
        }
    }
}
