/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import java.util.Objects;
import kp.ksl.compiler.InstructionCode;
import kp.ksl.compiler.InstructionCodeType;
import kp.ksl.compiler.meta.Function;
import kp.ksl.compiler.meta.MetaClass;
import kp.ksl.compiler.meta.Signature;
import kp.ksl.compiler.meta.Variable;
import org.apache.bcel.generic.Type;

/**
 *
 * @author Asus
 */
public abstract class KSLType extends MetaClass implements InstructionCode
{
    protected final String id;
    
    KSLType(String id, String name, Type javaType, Class<?> javaClass)
    {
        super(name, javaType, javaClass);
        if(id == null)
            throw new NullPointerException();
        this.id = id;
    }
    
    final String typeid() { return id; }
    
    public abstract boolean isMutable();
    
    public boolean isPrimitive() { return false; }
    public boolean isString() { return false; }
    public boolean isArray() { return false; }
    public boolean isStruct() { return false; }
    public boolean isReference() { return false; }
    public boolean isVoid() { return false; }
    
    /* Array Options */
    public short getDimension() { throw new UnsupportedOperationException(); }
    public KSLType getBaseType() { throw new UnsupportedOperationException(); }
    
    /* Field Options */
    public boolean isValidField(String field) { throw new UnsupportedOperationException(); }
    public Variable getField(String field) { throw new UnsupportedOperationException(); }
    public int getFieldCount() { throw new UnsupportedOperationException(); }
    
    /* Function Options */
    public boolean isValidFunction(Signature signature) { throw new UnsupportedOperationException(); }
    public Function getFunction(Signature signature) { throw new UnsupportedOperationException(); }
    public final Function getReferenceMethod(Signature signature)
    {
        Signature ms = signature.asMethodSignature();
        if(ms == null)
            throw new CompilationError("Expected an assignable");
    }
    
    
    /* Cast Operations */
    public boolean canCastTo(KSLType type) { throw new UnsupportedOperationException(); }
    
    /* Other */
    
    @Override
    public final boolean isScript() { return false; }
    
    @Override
    public final boolean isKSLType() { return true; }
    
    public boolean requireDoubleStackEntry() { return false; }
    
    public final boolean equals(KSLType other) { return this == other || typeid().equals(other.typeid()); }
    
    public final boolean is(KSLType other) { return this == other || typeid().equals(other.typeid()); }
    public final boolean is(String typeid) { return typeid().equals(typeid); }
    
    @Override
    public final boolean equals(Object o) { return o instanceof KSLType && equals((KSLType) o); }

    @Override
    public final int hashCode()
    {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(typeid());
        return hash;
    }
    
    @Override
    public final String toString() { return getName(); }
    
    @Override
    public final InstructionCodeType getInstructionCodeType() { return InstructionCodeType.TYPE; }
}
