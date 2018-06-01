/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import java.util.Collection;
import java.util.Objects;
import kp.ksl.compiler.InstructionCode;
import kp.ksl.compiler.InstructionCodeType;
import kp.ksl.compiler.exception.CompilationError;
import kp.ksl.compiler.meta.Function;
import kp.ksl.compiler.meta.MetaClass;
import kp.ksl.compiler.meta.Signature;
import kp.ksl.compiler.meta.Variable;
import kp.ksl.lang.KSLClassLoader;
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
    
    /* Method Options */
    public boolean isValidReferenceMethod(Signature signature) throws CompilationError { throw new UnsupportedOperationException(); }
    public Function getReferenceMethod(Signature signature) throws CompilationError { throw new UnsupportedOperationException(); }
    
    /* Script Function Options */
    public Collection<Function> getScriptFunctions() { throw new UnsupportedOperationException(); }
    
    
    /* Cast Operations */
    public abstract boolean isManualAssignableFrom(KSLType type);
    public abstract boolean isAutoAssignableFrom(KSLType type);
    public KSLReference getWrapperFromPrimitive(KSLClassLoader classLoader) { return null; }
    public KSLPrimitive getPrimitiveFromWrapper() { return null; }
    //public boolean canCastTo(KSLType type) { throw new UnsupportedOperationException(); }
    
    /* Other */
    
    @Override
    public final boolean isScript() { return false; }
    
    @Override
    public final boolean isKSLType() { return true; }
    
    public final boolean isJavaObjectClass() { return jclass == Object.class; }
    
    public boolean requireDoubleStackEntry() { return false; }
    
    public final boolean equals(KSLType other) { return is(other); }
    
    public final boolean is(KSLType other) { return this == other || jclass == other.jclass; }
    
    @Override
    public final boolean equals(Object o) { return o instanceof KSLType && is((KSLType) o); }

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
