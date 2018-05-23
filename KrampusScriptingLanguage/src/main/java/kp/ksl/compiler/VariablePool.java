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
import kp.ksl.compiler.types.KSLType;

/**
 *
 * @author Asus
 */
public final class VariablePool
{
    private final StaticAllocator staticAllocator;
    private final LocalAllocator localAllocator = new LocalAllocator();
    private final LinkedList<LocalScope> vars;
    
    
    private final class LocalScope
    {
        private final HashMap<String, Variable> vars = new HashMap<>();
        private int localCount = VariablePool.this.vars.isEmpty() ? -1 : VariablePool.this.vars.peek().localCount;
        
        private Variable create(String name, KSLType type, int ref, String classOwner, boolean isConst) throws CompilationError
        {
            if(vars.containsKey(name))
                throw new CompilationError("Variable " + name + " has already exists");
            Variable var = new Variable(name, type, ref, classOwner, isConst, classOwner != null && !isConst);
            vars.put(name, var);
            return var;
        }
        
        public final Variable createLocal(String name, KSLType type) throws CompilationError
        {
            localCount = localAllocator.allocate();
            return create(name, type, localCount, null, false);
        }
        
        public final Variable createStatic(String name, KSLType type) throws CompilationError
        {
            localCount = localAllocator.allocate();
            return create(name, type, localCount, null, false);
        }
        
        public final int getLocalCount() { return localCount; }
        //public final boolean exists()
    }
    
    public final class Variable
    {
        private final String name;
        private final KSLType type;
        private final int ref;
        private final String classOwner;
        private final boolean _const;
        private boolean initiated;
        
        private Variable(String name, KSLType type, int ref, String classOwner, boolean isConst, boolean initiated)
        {
            this.name = Objects.requireNonNull(name);
            this.type = Objects.requireNonNull(type);
            this.ref = ref;
            this.classOwner = classOwner;
            this._const = classOwner != null && isConst;
            this.initiated = initiated;
        }
        
        public final String getName() { return name; }
        public final KSLType getType() { return type; }
        public final int getReference() { return ref; }
        public final String getClassOwner() { return classOwner; }
        public final boolean isLocal() { return classOwner == null && !_const; }
        public final boolean isStatic() { return classOwner != null && !_const; }
        public final boolean isConst() { return _const; }
        public final boolean isInitiated() { return initiated; }
        
        public final void initiate()
        {
            if(initiated)
                throw new IllegalStateException();
            initiated = true;
        }
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
}
