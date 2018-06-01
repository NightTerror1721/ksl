/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import kp.ksl.compiler.exception.CompilationError;
import kp.ksl.compiler.meta.Function.Parameter;
import kp.ksl.compiler.meta.Signature;
import kp.ksl.compiler.types.KSLType;
import org.apache.bcel.generic.Type;

/**
 *
 * @author Asus
 */
public final class Namespace
{
    private final StaticAllocator staticAllocator;
    private final LocalAllocator localAllocator = new LocalAllocator();
    private final LinkedList<NamespaceScope> vars;
    
    public Namespace()
    {
        staticAllocator = null;
        vars = null;
    }
    
    
    private final class NamespaceScope
    {
        private final HashMap<String, Variable> vars = new HashMap<>();
        private final HashMap<Signature, Function> funcs = new HashMap<>();
        private int localCount = Namespace.this.vars.isEmpty() ? -1 : Namespace.this.vars.peek().localCount;
        
        private Variable createVar(String name, KSLType type, int ref, Type typeOwner, boolean isConst) throws CompilationError
        {
            if(vars.containsKey(name))
                throw new CompilationError("Variable " + name + " has already exists");
            Variable var = new Variable(name, type, ref, typeOwner, isConst, typeOwner != null && !isConst);
            vars.put(name, var);
            return var;
        }
        
        public final Variable createLocalVariable(String name, KSLType type) throws CompilationError
        {
            localCount = localAllocator.allocate();
            return createVar(name, type, localCount, null, false);
        }
        
        public final Variable createStaticVariable(String name, KSLType type) throws CompilationError
        {
            localCount = localAllocator.allocate();
            return createVar(name, type, localCount, null, false);
        }
        
        public final Function createFunction(Signature signature, KSLType returnType,  Parameter[] parameters, Type typeOwner)
        {
            
        }
        
        public final int getLocalCount() { return localCount; }
        //public final boolean exists()
    }
    
    private static final class LocalAllocator
    {
        private static final int MAX_VARS = 0xFF;
        private int allocated = 0;
        
        public final int allocate() throws CompilationError
        {
            allocated++;
            if(allocated > MAX_VARS)
                throw new CompilationError("Variable allocation capacity exceded");
            return allocated - 1;
        }
        
        public final int deallocate(int amount) throws CompilationError
        {
            if(amount < 1)
                throw new IllegalArgumentException();
            allocated -= amount;
            if(allocated < 0)
                throw new CompilationError("Variable counter is under zero");
            return allocated;
        }
        
        public final int peek() { return allocated; }
        
        public final int getAllocatedCount() { return allocated; }
    }
    
    private static final class StaticAllocator
    {
        private static final int MAX_VARS = 0xFFFF;
        private int allocated = 0;
        
        public final int allocate() throws CompilationError
        {
            allocated++;
            if(allocated > MAX_VARS)
                throw new CompilationError("Static Variable allocation capacity exceded");
            return allocated - 1;
        }
        
        public final int peek() { return allocated; }
        
        public final int getAllocatedCount() { return allocated; }
    }
    
    
    
    private final class Variable extends kp.ksl.compiler.meta.Variable
    {
        private final String name;
        private final KSLType type;
        private final int ref;
        private final Type typeOwner;
        private final boolean _const;
        private boolean initiated;
        
        private Variable(String name, KSLType type, int ref, Type typeOwner, boolean isConst, boolean initiated)
        {
            this.name = Objects.requireNonNull(name);
            this.type = Objects.requireNonNull(type);
            this.ref = ref;
            this.typeOwner = typeOwner;
            this._const = typeOwner != null && isConst;
            this.initiated = initiated;
        }
        
        @Override public final String getName() { return name; }
        @Override public final KSLType getType() { return type; }
        @Override public final int getLocalReference() { return ref; }
        @Override public final KSLType getTypeOwner() { throw new UnsupportedOperationException(); }
        @Override public final Type getTypeOwnerAsBcel() { return typeOwner; }
        @Override public final boolean isLocal() { return typeOwner == null && !_const; }
        @Override public final boolean isField() { return typeOwner != null && !_const; }
        @Override public final boolean isConst() { return _const; }
        @Override public final boolean isInitiated() { return initiated; }
        
        public final void initiate()
        {
            if(initiated)
                throw new IllegalStateException();
            initiated = true;
        }
    }
    
    
    private final class Function extends kp.ksl.compiler.meta.Function
    {
        private final Signature signature;
        private final Parameter[] pars;
        private final KSLType returnType;
        private final Type typeOwner;
        private final String javaName;
        
        private Function(Signature signature, KSLType returnType,  Parameter[] parameters, Type typeOwner, int localIndex)
        {
            this.signature = Objects.requireNonNull(signature);
            this.pars = Objects.requireNonNull(parameters);
            this.returnType = Objects.requireNonNull(returnType);
            this.typeOwner = Objects.requireNonNull(typeOwner);
            this.javaName = signature.getName() + "$" + localIndex;
        }
        
        @Override public final Signature getSignature() { return signature; }
        @Override public final int getParameterCount() { return pars.length; }
        @Override public final Parameter getParameter(int index) { return pars[index]; }
        @Override public final KSLType getReturnType() { return returnType; }
        @Override public final kp.ksl.compiler.meta.Variable getScriptOwnerInstance() { return null; }
        @Override public final KSLType getTypeOwner() { throw new UnsupportedOperationException(); }
        @Override public final Type getTypeOwnerAsBcel() { return typeOwner; }
    }
}
