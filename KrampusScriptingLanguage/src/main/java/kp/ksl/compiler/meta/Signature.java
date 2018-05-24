/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.meta;

import java.util.Arrays;
import java.util.Objects;
import kp.ksl.compiler.types.KSLType;

public final class Signature
{
    private final String name;
    private final KSLType[] pars;
    private int hash;
    
    public Signature(String name, KSLType[] parameterTypes)
    {
        this.name = Objects.requireNonNull(name);
        this.pars = Objects.requireNonNull(parameterTypes);
    }
    
    public final String getName() { return name; }
    
    public final int getParameterCount() { return pars.length; }
    public final KSLType getParameterType(int index) { return pars[index]; }
    
    public final Class<?>[] getJavaParameterTypes()
    {
        Class<?>[] types = new Class<?>[pars.length];
        for(int i=0;i<types.length;i++)
            types[i] = pars[i].jclass;
        return types;
    }
    
    public final Signature asMethodSignature()
    {
        if(pars.length < 1)
            return null;
        return new Signature(name, Arrays.copyOfRange(pars, 1, pars.length));
    }
    
    @Override
    public final int hashCode()
    {
        if(hash != 0)
            return hash;
        return hash = Objects.hash(name, Arrays.hashCode(pars));
    }

    @Override
    public final boolean equals(Object obj)
    {
        return this == obj || (obj != null && obj instanceof Signature && equals((Signature) obj));
    }
    
    public final boolean equals(final Signature s)
    {
        return Objects.equals(name, s.name) && Arrays.equals(pars, s.pars);
    }
}
