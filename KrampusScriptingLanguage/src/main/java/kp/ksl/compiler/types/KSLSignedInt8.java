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
public final class KSLSignedInt8 extends KSLPrimitive
{
    public KSLSignedInt8() { super(Typeid.SINT8, Typename.integerName(TypeModifier.SIGNED_BYTE), BasicType.BYTE, Byte.TYPE, Byte.class); }
    
    @Override
    public final boolean isAutoAssignableFrom(KSLType type)
    {
        if(is(type))
            return true;
        switch(type.typeid())
        {
            case Typeid.BOOLEAN:
                return true;
            default: return false;
        }
    }
}
