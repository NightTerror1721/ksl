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
    public KSLUnsignedInt32() { super(Typeid.UINT32, Typename.integerName(TypeModifier.UNSIGNED), new ObjectType(UnsignedInteger.class.getName())); }
}
