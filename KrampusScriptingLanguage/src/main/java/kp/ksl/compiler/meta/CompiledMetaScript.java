/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.meta;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Objects;
import kp.ksl.compiler.exception.CompilationError;
import kp.ksl.compiler.preprocessor.MacroRepository;
import kp.ksl.compiler.types.KSLType;
import kp.ksl.lang.KSLClassLoader;
import org.apache.bcel.generic.Type;

/**
 *
 * @author Asus
 */
public class CompiledMetaScript extends MetaScript
{
    private final KSLClassLoader classLoader;
    private final HashMap<String, ScriptField> fields = new HashMap<>();
    private final HashMap<Signature, ScriptMethod> funcs = new HashMap<>();
    private MacroRepository macros = MacroRepository.IMMUTABLE_EMPTY;
    private StructRepository structs = StructRepository.IMMUTABLE_EMPTY;
    private final ScriptReference sref;
    
    private CompiledMetaScript(Class<?> jclass, KSLClassLoader classLoader, KSLScript a)
    {
        super(jclass);
        this.classLoader = Objects.requireNonNull(classLoader);
        this.sref = new ScriptReference(Objects.requireNonNull(a));
    }
    
    private static boolean isScriptInstancePresent(Class<?> jclass, KSLScript a)
    {
        try
        {
            Field field = jclass.getField(a.instanceName());
            if(field == null)
                return false;
            if(field.getType() != jclass)
                return false;
            if(!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers()) ||
                    !Modifier.isPublic(field.getModifiers()))
                return false;
            return field.get(null) != null;
        }
        catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) { return false; }
    }
    
    private static CompiledMetaScript findMetaScriptCache(Class<?> jclass, KSLScript a)
    {
        try
        {
            Field field = jclass.getField(a.cacheName());
            if(field == null)
                return null;
            if(field.getType() != CompiledMetaScript.class)
                return null;
            if(!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers()) ||
                    !Modifier.isPublic(field.getModifiers()))
                return null;
            return (CompiledMetaScript) field.get(null);
        }
        catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) { return null; }
    }
    
    private static void setMetaScriptCache(Class<?> jclass, KSLScript a, CompiledMetaScript s)
    {
        try
        {
            Field field = jclass.getField(a.cacheName());
            if(field == null)
                return;
            if(field.getType() != CompiledMetaScript.class)
                return;
            if(!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers()) ||
                    !Modifier.isPublic(field.getModifiers()))
                return;
            field.set(null, s);
        }
        catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {}
    }
    
    private static void findMacrosRepository(Class<?> jclass, KSLScript a, CompiledMetaScript s)
    {
        s.macros = MacroRepository.IMMUTABLE_EMPTY;
        try
        {
            Field field = jclass.getField(a.macrosRepository());
            if(field == null)
                return;
            if(!MacroRepository.class.isAssignableFrom(field.getType()))
                return;
            if(!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers()) ||
                    !Modifier.isPublic(field.getModifiers()))
                return;
            MacroRepository r = (MacroRepository) field.get(null);
            if(r == null)
                return;
            s.macros = r;
        }
        catch(IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {}
    }
    
    private static void findStructsRepository(Class<?> jclass, KSLScript a, CompiledMetaScript s)
    {
        s.structs = StructRepository.IMMUTABLE_EMPTY;
        try
        {
            Field field = jclass.getField(a.structsRepository());
            if(field == null)
                return;
            if(!StructRepository.class.isAssignableFrom(field.getType()))
                return;
            if(!Modifier.isStatic(field.getModifiers()) || !Modifier.isFinal(field.getModifiers()) ||
                    !Modifier.isPublic(field.getModifiers()))
                return;
            StructRepository r = (StructRepository) field.get(null);
            if(r == null)
                return;
            s.structs = r;
        }
        catch(IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {}
    }
    
    public static final CompiledMetaScript createMetaScript(Class<?> jclass, KSLClassLoader classLoader)
    {
        if(!jclass.isAnnotationPresent(KSLScript.class))
            return null;
        KSLScript a = jclass.getAnnotation(KSLScript.class);
        if(!isScriptInstancePresent(jclass, a))
            return null;
        
        CompiledMetaScript s = findMetaScriptCache(jclass, a);
        if(s != null)
            return s;
        
        s = new CompiledMetaScript(jclass, classLoader, a);
        findMacrosRepository(jclass, a, s);
        findStructsRepository(jclass, a, s);
        setMetaScriptCache(jclass, a, s);
        
        return s;
    }

    @Override
    public final boolean isScript() { return true; }

    @Override
    public final boolean isKSLType() { return false; }
    
    
    private ScriptField findField(String name)
    {
        ScriptField rf = fields.getOrDefault(name, null);
        if(rf != null)
            return rf;
        try
        {
            Field f = jclass.getField(name);
            if(f == null)
                return null;
            if(!Modifier.isPublic(f.getModifiers()))
                return null;
            rf = new ScriptField(f);
            fields.put(rf.getName(), rf);
            return rf;
        }
        catch(NoSuchFieldException | SecurityException ex) { return null; }
    }
    
    private ScriptMethod findFunction(Signature signature) throws CompilationError
    {
        ScriptMethod sm = funcs.getOrDefault(signature, null);
        if(sm != null)
            return sm;
        try
        {
            Class<?>[] jtypes = signature.getJavaParameterTypes();
            Method m = jclass.getMethod(signature.getName(), jtypes);
            if(m == null)
                return null;
            if(!Modifier.isPublic(m.getModifiers()))
                return null;
            sm = new ScriptMethod(signature, m);
            funcs.put(signature, sm);
            return sm;
        }
        catch(ClassNotFoundException | NoSuchMethodException | SecurityException ex) { return null; }
    }

    @Override
    public final boolean isValidField(String field) throws CompilationError { return findField(field) != null; }

    @Override
    public final Variable getField(String field) throws CompilationError { return findField(field); }

    @Override
    public final boolean isValidScriptFunction(Signature signature) throws CompilationError { return findFunction(signature) != null; }

    @Override
    public final Function getScriptFunction(Signature signature) throws CompilationError { return findFunction(signature); }

    @Override
    public final MacroRepository getMacros() { return macros; }

    @Override
    public final StructRepository getStructs() { return structs; }
    
    private final class ScriptField extends Variable
    {
        private final Field field;
        private final boolean _const;
        private KSLType type;
        
        private ScriptField(Field field)
        {
            super();
            this.field = field;
            this._const = Modifier.isFinal(field.getModifiers());
        }
        
        @Override
        public final KSLType getTypeOwner() { throw new UnsupportedOperationException(); }
        
        @Override
        public final Type getTypeOwnerAsBcel() { return CompiledMetaScript.this.jtype; }
        
        @Override
        public final String getName() { return field.getName(); }

        @Override
        public final KSLType getType()
        {
            if(type != null)
                return type;
            try { return type = classLoader.findKSLType(field.getType()); }
            catch(ClassNotFoundException ex) { throw new RuntimeException(ex); }
        }

        @Override
        public final int getLocalReference() { return -1; }

        @Override
        public final boolean isLocal() { return false; }

        @Override
        public final boolean isField() { return true; }

        @Override
        public final boolean isConst() { return _const; }

        @Override
        public final boolean isInitiated() { return true; }

        @Override
        public final ScriptReferenceField getScriptOwnerInstance() { return sref; }
    }
    
    
    private final class ScriptMethod extends Function
    {
        private final Signature signature;
        private final KSLType returnType;
        private final ScriptMethodParameter[] pars;
        
        private ScriptMethod(Signature signature, Method method) throws ClassNotFoundException
        {
            this.signature = signature;
            this.pars = new ScriptMethodParameter[method.getParameterCount()];
            java.lang.reflect.Parameter[] jpars = method.getParameters();
            for(int i=0;i<pars.length;i++)
                this.pars[i] = new ScriptMethodParameter(jpars[i]);
            this.returnType = classLoader.findKSLType(method.getReturnType());
        }
        
        @Override
        public final Signature getSignature() { return signature; }

        @Override
        public final int getParameterCount() { return pars.length; }

        @Override
        public final Parameter getParameter(int index) { return pars[index]; }

        @Override
        public final KSLType getReturnType() { return returnType; }

        @Override
        public ScriptReferenceField getScriptOwnerInstance() { return null; }

        @Override
        public final KSLType getTypeOwner() { throw new UnsupportedOperationException(); }
        
        @Override
        public final Type getTypeOwnerAsBcel() { return jtype; }
        
    }
    
    private final class ScriptMethodParameter extends Function.Parameter
    {
        private ScriptMethodParameter(java.lang.reflect.Parameter jpar) throws ClassNotFoundException
        {
            super(jpar.getName(), classLoader.findKSLType(jpar.getType()), jpar.isVarArgs());
        }
    }
    
    private final class ScriptReference extends ScriptReferenceField
    {
        private final String name;
        
        private ScriptReference(KSLScript a)
        {
            this.name = a.instanceName();
        }
        
        @Override
        public final String getName() { return name; }

        @Override
        public final Type getBcelType() { return jtype; }
    }
}
