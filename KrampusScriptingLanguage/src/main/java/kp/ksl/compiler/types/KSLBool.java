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
public final class KSLBool extends KSLPrimitive
{
    public KSLBool() { super(Typeid.BOOLEAN, Typename.BOOLEAN, BasicType.BOOLEAN, Boolean.TYPE, Boolean.class); }
    
    @Override
    public final boolean isAutoAssignableFrom(KSLType type)
    {
        return is(type);
    }
}
