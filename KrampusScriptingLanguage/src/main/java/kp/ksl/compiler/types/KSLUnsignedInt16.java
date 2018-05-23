/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import kp.ksl.lang.UnsignedShortInteger;
import org.apache.bcel.generic.ObjectType;

/**
 *
 * @author Asus
 */
public final class KSLUnsignedInt16 extends KSLPrimitive
{
    public KSLUnsignedInt16() { super(Typeid.UINT16,
            Typename.integerName(TypeModifier.UNSIGNED_SHORT),
            new ObjectType(UnsignedShortInteger.class.getName()),
            UnsignedShortInteger.class); }
}
