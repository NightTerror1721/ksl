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
public final class KSLSignedInt32 extends KSLPrimitive
{
    public KSLSignedInt32() { super(Typeid.SINT32, Typename.integerName(TypeModifier.SIGNED), BasicType.INT, Integer.TYPE, Integer.class); }
    
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
                return true;
            default: return false;
        }
    }
}
