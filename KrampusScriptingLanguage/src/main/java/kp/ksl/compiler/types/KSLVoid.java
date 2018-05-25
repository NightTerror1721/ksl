/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import org.apache.bcel.generic.Type;

/**
 *
 * @author Asus
 */
public final class KSLVoid extends KSLType
{

    public KSLVoid() {
        super(Typeid.VOID, Typename.VOID, Type.VOID, Void.TYPE);
    }

    @Override
    public final boolean isMutable() { return false; }

    @Override
    public final boolean isVoid() { return true; }

    @Override
    public final boolean isManualAssignableFrom(KSLType type) { return false; }

    @Override
    public final boolean isAutoAssignableFrom(KSLType type) { return false; }
    
}
