/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import kp.ksl.lang.UnsignedByteInteger;
import org.apache.bcel.generic.ObjectType;

/**
 *
 * @author Asus
 */
public final class KSLUnsignedInt8 extends KSLPrimitive
{
    public KSLUnsignedInt8() { super(Typeid.UINT8,
            Typename.integerName(TypeModifier.UNSIGNED_BYTE),
            new ObjectType(UnsignedByteInteger.class.getName()),
            UnsignedByteInteger.class,
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
                return true;
            default: return false;
        }
    }
}
