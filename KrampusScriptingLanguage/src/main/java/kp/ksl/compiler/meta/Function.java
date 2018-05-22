/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.meta;

import java.util.Objects;
import kp.ksl.compiler.types.KSLType;
import org.apache.bcel.generic.Type;

/**
 *
 * @author Asus
 */
public abstract class Function extends MetaObject
{
    private final String name;
    private final Parameter[] pars;
    
    protected Function(Type typeOwner, String name, Parameter[] parameters)
    {
        super(typeOwner);
        this.name = Objects.requireNonNull(name);
        this.pars = Objects.requireNonNull(parameters);
    }
    
    public final String getName() { return name; }
    
    public final int getParameterCount() { return pars.length; }
    public final Parameter getParameter(int index) { return pars[index]; }
    
    public abstract KSLType getReturnType();
    
    public static abstract class Parameter
    {
        private final String name;
        private final boolean varargs;
        
        protected Parameter(String name, boolean isVarargs)
        {
            this.name = name;
            this.varargs = isVarargs;
        }
        
        public final String getName() { return name; }
        public final boolean isVarargs() { return varargs; }
        
        public abstract KSLType getType();
    }
}
