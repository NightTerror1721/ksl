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
public abstract class Variable extends MetaObject
{
    protected final String name;
    
    protected Variable(Type typeOwner, String name)
    {
        super(typeOwner);
        this.name = Objects.requireNonNull(name);
    }
    
    public final String getName() { return name; }
    public abstract KSLType getType();
    public abstract int getLocalReference();
    
    public abstract boolean isLocal();
    public abstract boolean isStatic();
    public abstract boolean isConst();
    public abstract boolean isInitiated();
}
