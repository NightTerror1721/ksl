/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import org.apache.bcel.generic.ObjectType;

/**
 *
 * @author Asus
 */
public final class KSLReference extends KSLType
{
    static final KSLReference UNDEFINED_REFERENCE = new KSLReference(Object.class);
    
    private KSLReference(Class<?> jclass) { super(Typeid.of(jclass), Typename.REFERENCE, new ObjectType(jclass.getName()), jclass); }
    
    @Override
    public final boolean isMutable() { return false; }

    @Override
    public final boolean isReference() { return true; }

    @Override
    public final boolean canCastTo(KSLType type) { return is(type); }
    
    
    
    public static final KSLReference createReference(Class<?> javaClass)
    {
        return null;
    }
}
