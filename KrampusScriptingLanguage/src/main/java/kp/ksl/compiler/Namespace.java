/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
import kp.ksl.compiler.exception.CompilationError;
import kp.ksl.compiler.meta.Function.Parameter;
import kp.ksl.compiler.meta.MetaScript.ScriptReferenceField;
import kp.ksl.compiler.meta.Signature;
import kp.ksl.compiler.preprocessor.Macro;
import kp.ksl.compiler.types.KSLStruct;
import kp.ksl.compiler.types.KSLType;
import kp.ksl.lang.KSLClassLoader;
import org.apache.bcel.generic.Type;

/**
 *
 * @author Asus
 */
public final class Namespace
{
    private final LocalVariableAllocator localVarAllocator = new LocalVariableAllocator();
    private final LocalFunctionAllocator localFuncAllocator = new LocalFunctionAllocator();
    private final LinkedList<NamespaceScope> scopes = new LinkedList<>();
    private final HashMap<String, Macro> macros = new HashMap<>();
    private final ExternManager extern;
    
    public Namespace(KSLClassLoader classLoader)
    {
        extern = new ExternManager(classLoader);
    }
    
    public final boolean hasMacro(String name) throws CompilationError
    {
        return macros.containsKey(name) || extern.hasMacro(name);
    }
    
    public final Macro getMacro(String name) throws CompilationError
    {
        Macro macro = macros.get(name);
        return macro != null ? macro : extern.getMacro(name);
    }
    
    private <Id, El> El findElementInScope(Id id, Has<NamespaceScope, Id> has, Get<NamespaceScope, Id, El> get) throws CompilationError
    {
        ListIterator<NamespaceScope> it = scopes.listIterator(scopes.size() - 1);
        while(it.hasPrevious())
        {
            NamespaceScope scope = it.previous();
            if(has.has(scope, id))
                return get.get(scope, id);
        }
        return null;
    }
    
    public final boolean hasVariable(String name) throws CompilationError
    {
        kp.ksl.compiler.meta.Variable var = findElementInScope(name, NamespaceScope::hasVariable, NamespaceScope::getVariable);
        return var != null ? true : extern.hasVariable(name);
    }
    public final kp.ksl.compiler.meta.Variable getVariable(String name) throws CompilationError
    {
        kp.ksl.compiler.meta.Variable var = findElementInScope(name, NamespaceScope::hasVariable, NamespaceScope::getVariable);
        return var != null ? var : extern.getVariable(name);
    }
    
    public final boolean hasFunction(Signature signature) throws CompilationError
    {
        kp.ksl.compiler.meta.Function func = findElementInScope(signature, NamespaceScope::hasFunction, NamespaceScope::getFunction);
        return func != null ? true : extern.hasFunction(signature);
    }
    public final kp.ksl.compiler.meta.Function getFunction(Signature signature) throws CompilationError
    {
        kp.ksl.compiler.meta.Function func = findElementInScope(signature, NamespaceScope::hasFunction, NamespaceScope::getFunction);
        return func != null ? func : extern.getFunction(signature);
    }
    
    public final boolean hasStruct(String name) throws CompilationError
    {
        KSLStruct type = findElementInScope(name, NamespaceScope::hasStruct, NamespaceScope::getStruct);
        return type != null ? true : extern.hasStruct(name);
    }
    public final KSLStruct getStruct(String name) throws CompilationError
    {
        KSLStruct type = findElementInScope(name, NamespaceScope::hasStruct, NamespaceScope::getStruct);
        return type != null ? type : extern.getStruct(name);
    }
    
    
    public final kp.ksl.compiler.meta.Variable createLocalVariable(String name, KSLType type) throws CompilationError
    {
        if(scopes.isEmpty())
            throw new IllegalStateException();
        return scopes.getLast().createLocalVariable(name, type);
    }
    
    public final kp.ksl.compiler.meta.Variable createStaticVariable(String name, KSLType type) throws CompilationError
    {
        if(scopes.isEmpty())
            throw new IllegalStateException();
        return scopes.getLast().createStaticVariable(name, type);
    }
    
    public final kp.ksl.compiler.meta.Function createFunction(Signature signature, KSLType returnType,  Parameter[] parameters, Type typeOwner) throws CompilationError
    {
        if(scopes.isEmpty())
            throw new IllegalStateException();
        return scopes.getLast().createFunction(signature, returnType, parameters, typeOwner);
    }
    
    public final KSLStruct registerStruct(String name, KSLStruct struct) throws CompilationError
    {
        if(scopes.isEmpty())
            throw new IllegalStateException();
        return scopes.getLast().registerStruct(name, struct);
    }
    
    
    
    
    private final class NamespaceScope
    {
        private final HashMap<String, Variable> vars = new HashMap<>();
        private final HashMap<Signature, Function> funcs = new HashMap<>();
        private final HashMap<String, KSLStruct> structs = new HashMap<>();
        private final boolean isRootScope = Namespace.this.scopes.isEmpty();
        private int localVarCount = isRootScope ? -1 : Namespace.this.scopes.peek().localVarCount;
        
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
            localVarCount = localVarAllocator.allocate();
            return createVar(name, type, localVarCount, null, false);
        }
        
        public final Variable createStaticVariable(String name, KSLType type) throws CompilationError
        {
            localVarCount = localVarAllocator.allocate();
            return createVar(name, type, localVarCount, null, false);
        }
        
        public final Function createFunction(Signature signature, KSLType returnType,  Parameter[] parameters, Type typeOwner) throws CompilationError
        {
            if(funcs.containsKey(signature))
                throw new CompilationError("Function " + signature + " has already exists");
            Function func = new Function(signature, returnType, parameters, typeOwner, isRootScope ? -1 : localFuncAllocator.allocate());
            funcs.put(signature, func);
            return func;
        }
        
        public final KSLStruct registerStruct(String name, KSLStruct struct) throws CompilationError
        {
            if(structs.containsKey(name))
                throw new CompilationError("struct " + name + " has already exists");
            structs.put(name, Objects.requireNonNull(struct));
            return struct;
        }
        
        public final int getLocalVariableCount() { return localVarCount; }
        
        
        public final boolean hasVariable(String name) { return vars.containsKey(name); }
        public final boolean hasFunction(Signature signature) { return funcs.containsKey(signature); }
        public final boolean hasStruct(String name) { return structs.containsKey(name); }
        
        public final Variable getVariable(String name) throws CompilationError
        {
            Variable var = vars.getOrDefault(name, null);
            if(var == null)
                throw new CompilationError("Variable " + name + " does not exists");
            return var;
        }
        public final Function getFunction(Signature signature) throws CompilationError
        {
            Function func = funcs.getOrDefault(signature, null);
            if(func == null)
                throw new CompilationError("Function " + signature + " does not exists");
            return func;
        }
        public final KSLStruct getStruct(String name) throws CompilationError
        {
            KSLStruct type = structs.getOrDefault(name, null);
            if(type == null)
                throw new CompilationError("struct " + name + " does not exists");
            return type;
        }
    }
    
    private static final class LocalVariableAllocator
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
    
    private static final class LocalFunctionAllocator
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
        @Override public final ScriptReferenceField getScriptOwnerInstance() { return null; }
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
            this.javaName = signature.getName() + (localIndex < 0 ? "" : ("$" + localIndex));
        }
        
        @Override public final Signature getSignature() { return signature; }
        @Override public final int getParameterCount() { return pars.length; }
        @Override public final Parameter getParameter(int index) { return pars[index]; }
        @Override public final KSLType getReturnType() { return returnType; }
        @Override public final ScriptReferenceField getScriptOwnerInstance() { return null; }
        @Override public final KSLType getTypeOwner() { throw new UnsupportedOperationException(); }
        @Override public final Type getTypeOwnerAsBcel() { return typeOwner; }
    }
    
    @FunctionalInterface
    interface Has<Base, Id> { boolean has(Base base, Id id) throws CompilationError; }
    
    @FunctionalInterface
    interface Get<Base, Id, El> { El get(Base base, Id id) throws CompilationError; }
}
