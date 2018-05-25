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
public final class KSLSignedInt16 extends KSLPrimitive
{
    public KSLSignedInt16() { super(Typeid.SINT16, Typename.integerName(TypeModifier.SIGNED_SHORT), BasicType.SHORT, Short.TYPE, Short.class); }
    
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
                return true;
            default: return false;
        }
    }
}
