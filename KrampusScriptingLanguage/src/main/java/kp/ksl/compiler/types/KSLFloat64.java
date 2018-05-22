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
public final class KSLFloat64 extends KSLPrimitive
{
    public KSLFloat64() { super(Typeid.FLOAT64, Typename.floatName(TypeModifier.SIGNED_LONG), BasicType.DOUBLE); }
    
    @Override
    public final boolean requireDoubleStackEntry() { return true; }
}
