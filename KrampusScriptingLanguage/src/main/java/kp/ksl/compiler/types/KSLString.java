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
public final class KSLString extends KSLType
{
    public KSLString() { super(Typeid.STRING, Typename.STRING, BasicType.STRING, String.class); }
    
    @Override
    public final boolean isMutable() { return false; }
    
    @Override
    public final boolean isString() { return true; }

    @Override
    public final boolean canCastTo(KSLType type) { return is(type); }
    
}
