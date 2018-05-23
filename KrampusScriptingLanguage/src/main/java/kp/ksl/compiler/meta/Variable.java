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
public abstract class Variable extends MetaObject
{ 
    protected Variable() {}
    
    public abstract String getName();
    public abstract KSLType getType();
    public abstract int getLocalReference();
    
    public abstract boolean isLocal();
    public abstract boolean isStatic();
    public abstract boolean isConst();
    public abstract boolean isInitiated();
}
