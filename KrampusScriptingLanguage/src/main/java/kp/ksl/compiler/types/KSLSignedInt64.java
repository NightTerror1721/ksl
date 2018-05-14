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
    public KSLSignedInt64() { super(Typeid.SINT64, Typename.integerName(Modifier.SIGNED_LONG), BasicType.LONG); }
}
