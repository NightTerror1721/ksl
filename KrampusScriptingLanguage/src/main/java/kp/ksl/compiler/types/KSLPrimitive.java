/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import java.util.Objects;
import kp.ksl.lang.KSLClassLoader;
import org.apache.bcel.generic.Type;

/**
 *
 * @author Asus
 */
public abstract class KSLPrimitive extends KSLType
{
    private final Class<?> wrapper;
    
    KSLPrimitive(String id, String name, Type javaType, Class<?> javaClass, Class<?> wrapper)
    {
        super(id, name, javaType, javaClass);
        this.wrapper = Objects.requireNonNull(wrapper);
    }
    
    @Override
    public final boolean isMutable() { return false; }
    
    @Override
    public final boolean isPrimitive() { return true; }
    
    @Override
    public final boolean isManualAssignableFrom(KSLType type)
    {
        return is(type) || type.isPrimitive();
    }
    
    @Override
    public final KSLReference getWrapperFromPrimitive(KSLClassLoader classLoader)
    {
        try { return (KSLReference) classLoader.findKSLType(wrapper); }
        catch(ClassNotFoundException ex)
        {
            ex.printStackTrace(System.err);
            return null;
        }
    }
}
