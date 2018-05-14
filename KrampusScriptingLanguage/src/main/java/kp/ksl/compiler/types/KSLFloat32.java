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
public final class KSLFloat32 extends KSLPrimitive
{
    public KSLFloat32() { super(Typeid.FLOAT32, Typename.floatName(Modifier.SIGNED), BasicType.FLOAT); }
}
