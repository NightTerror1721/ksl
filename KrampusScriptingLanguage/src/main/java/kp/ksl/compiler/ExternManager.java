/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import kp.ksl.compiler.Namespace.Get;
import kp.ksl.compiler.Namespace.Has;
import kp.ksl.compiler.exception.CompilationError;
import kp.ksl.compiler.meta.Function;
import kp.ksl.compiler.meta.MetaScript;
import kp.ksl.compiler.meta.Signature;
import kp.ksl.compiler.meta.Variable;
import kp.ksl.compiler.preprocessor.Macro;
import kp.ksl.compiler.types.KSLStruct;
import kp.ksl.lang.KSLClassLoader;
import kp.ksl.lang.Struct;

/**
 *
 * @author Asus
 */
public final class ExternManager
{
    private final KSLClassLoader classLoader;
    private final HashSet<String> scriptPaths = new HashSet<>();
    private final LinkedList<MetaScript> scripts = new LinkedList<>();
    
    private final HashMap<String, Variable> varCache = new HashMap<>();
    private final HashMap<Signature, Function> funcCache = new HashMap<>();
    private final HashMap<String, Macro> macroCache = new HashMap<>();
    private final HashMap<String, KSLStruct> structCache = new HashMap<>();
    
    
    public ExternManager(KSLClassLoader classLoader)
    {
        this.classLoader = Objects.requireNonNull(classLoader);
    }
    
    private <Id, El> El findElement(Id id, HashMap<Id, El> cache,
            Has<MetaScript, Id> has, Get<MetaScript, Id, El> get) throws CompilationError
    {
        El e = cache.getOrDefault(id, null);
        if(e != null)
            return e;
        
        for(MetaScript script : scripts)
            if(has.has(script, id))
            {
                e = get.get(script, id);
                break;
            }
        
        if(e != null)
            cache.put(id, e);
        return e;
    }
    
    private KSLStruct findStruct(String name)
    {
        KSLStruct type = structCache.getOrDefault(name, null);
        if(type != null)
            return type;
        
        for(MetaScript script : scripts)
            if(script.getStructs().hasStructClass(name))
            {
                Class<? extends Struct> jclass = script.getStructs().getStructClass(name);
                type = KSLStruct.createStruct(jclass, classLoader);
                if(type != null)
                    break;
            }
        
        if(type != null)
            structCache.put(name, type);
        return type;
    }
    
    public final boolean hasVariable(String name) throws CompilationError { return findElement(name, varCache, MetaScript::isValidField, MetaScript::getField) != null; }
    public final Variable getVariable(String name) throws CompilationError { return findElement(name, varCache, MetaScript::isValidField, MetaScript::getField); }
    
    public final boolean hasFunction(Signature signature) throws CompilationError { return findElement(signature, funcCache, MetaScript::isValidScriptFunction, MetaScript::getScriptFunction) != null; }
    public final Function getFunction(Signature signature) throws CompilationError { return findElement(signature, funcCache, MetaScript::isValidScriptFunction, MetaScript::getScriptFunction); }
    
    private static final Has<MetaScript, String> FIND_HAS_MACRO = (s, id) -> s.getMacros().hasMacro(id);
    private static final Get<MetaScript, String, Macro> FIND_GET_MACRO = (s, id) -> s.getMacros().getMacro(id);
    public final boolean hasMacro(String name) throws CompilationError { return findElement(name, macroCache, FIND_HAS_MACRO, FIND_GET_MACRO) != null; }
    public final Macro getMacro(String name) throws CompilationError { return findElement(name, macroCache, FIND_HAS_MACRO, FIND_GET_MACRO); }
    
    public final boolean hasStruct(String name) throws CompilationError { return findStruct(name) != null; }
    public final KSLStruct getStruct(String name) throws CompilationError { return findStruct(name); }
    
}
