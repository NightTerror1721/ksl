/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.meta;

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
    
    public abstract int getParameterCount();
    public abstract Parameter getParameter(int index);
    
    public abstract KSLType getReturnType();
    
    public abstract String getScriptOwnerInstance();
    
    public static abstract class Parameter
    {
        protected Parameter() {}
        
        public abstract String getName();
        public abstract KSLType getType();
        public abstract boolean isVarargs();
    }
}
