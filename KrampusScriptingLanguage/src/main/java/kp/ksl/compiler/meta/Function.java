/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.meta;

import java.util.Objects;
import kp.ksl.compiler.types.KSLType;

/**
 *
 * @author Asus
 */
public abstract class Function extends MetaObject
{
    protected Function() {}
    
    public abstract Signature getSignature();
    public final String getName() { return getSignature().getName(); }
    public String getJavaName() { return getSignature().getName(); }
    
    public abstract int getParameterCount();
    public abstract Parameter getParameter(int index);
    
    public abstract KSLType getReturnType();
    
    public static class Parameter
    {
        protected final String name;
        protected final KSLType type;
        protected final boolean varargs;
        
        protected Parameter(String name, KSLType type, boolean varargs)
        {
            this.name = Objects.requireNonNull(name);
            this.type = Objects.requireNonNull(type);
            this.varargs = Objects.requireNonNull(varargs);
        }
        
        public final String getName() { return name; }
        public final KSLType getType() { return type; }
        public final boolean isVarargs() { return varargs; }
    }
}
