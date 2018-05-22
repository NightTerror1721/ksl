/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.meta;

import java.util.Objects;
import org.apache.bcel.generic.Type;

/**
 *
 * @author Asus
 */
public abstract class MetaObject
{
    protected final Type typeOwner;
    
    MetaObject(Type typeOwner) { this.typeOwner = Objects.requireNonNull(typeOwner); }
    
    public final Type getTypeOwner() { return typeOwner; }
}
