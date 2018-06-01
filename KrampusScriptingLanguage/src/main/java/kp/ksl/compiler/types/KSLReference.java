/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import kp.ksl.compiler.exception.CompilationError;
import kp.ksl.compiler.meta.Function;
import kp.ksl.compiler.meta.Signature;
import kp.ksl.compiler.meta.Variable;
import kp.ksl.lang.KSLClassLoader;
import org.apache.bcel.generic.ObjectType;

/**
 *
 * @author Asus
 */
public final class KSLReference extends KSLType
{
    private final KSLClassLoader classLoader;
    private final HashMap<String, ReferenceField> fields = new HashMap<>();
    private final HashMap<Signature, ReferenceMethod> funcs = new HashMap<>();
    
    private KSLReference(Class<?> jclass, KSLClassLoader classLoader)
    {
        super(Typeid.of(jclass), Typename.REFERENCE, new ObjectType(jclass.getName()), jclass);
        this.classLoader = classLoader;
    }
    
    @Override
    public final boolean isMutable() { return false; }

    @Override
    public final boolean isReference() { return true; }

    @Override
    public final boolean isManualAssignableFrom(KSLType type)
    {
        return is(type) || (type.isReference() &&
                (jclass.isAssignableFrom(type.getJavaClass()) || type.getJavaClass().isAssignableFrom(jclass)));
    }

    @Override
    public final boolean isAutoAssignableFrom(KSLType type)
    {
        return is(type) || (type.isReference() && jclass.isAssignableFrom(type.getJavaClass()));
    }
    
    
    private ReferenceField findField(String name)
    {
        ReferenceField rf = fields.getOrDefault(id, null);
        if(rf != null)
            return rf;
        try
        {
            Field f = jclass.getField(name);
            if(f == null)
                return null;
            if(!Modifier.isPublic(f.getModifiers()))
                return null;
            rf = new ReferenceField(f);
            fields.put(rf.getName(), rf);
            return rf;
        }
        catch(NoSuchFieldException | SecurityException ex) { return null; }
    }
    
    @Override
    public final boolean isValidField(String field) { return findField(name) != null; }
    
    @Override
    public final Variable getField(String field) { return findField(name); }
    
    
    private Signature parseSignature(Signature signature) throws CompilationError
    {
        Signature ms = signature.asMethodSignature();
        if(ms == null)
            throw new CompilationError("Expected minimum one argument in refernce method call");
        if(!isAutoAssignableFrom(signature.getParameterType(0)))
            throw new CompilationError("Cannot assign " + signature.getParameterType(0) + " into " + this);
        return ms;
    }
    
    private ReferenceMethod findFunction(Signature signature) throws CompilationError
    {
        signature = parseSignature(signature);
        ReferenceMethod rm = funcs.getOrDefault(signature, null);
        if(rm != null)
            return rm;
        try
        {
            Class<?>[] jtypes = signature.getJavaParameterTypes();
            Method m = jclass.getMethod(signature.getName(), jtypes);
            if(m == null)
                return null;
            if(!Modifier.isPublic(m.getModifiers()))
                return null;
            rm = new ReferenceMethod(signature, m);
            funcs.put(signature, rm);
            return rm;
        }
        catch(ClassNotFoundException | NoSuchMethodException | SecurityException ex) { return null; }
    }
    
    @Override
    public final boolean isValidReferenceMethod(Signature signature) throws CompilationError
    {
        return findFunction(signature) != null;
    }
    
    @Override
    public final Function getReferenceMethod(Signature signature) throws CompilationError
    {
        return findFunction(signature);
    }
    
    
    public static final KSLReference createReference(Class<?> javaClass, KSLClassLoader loader)
    {
        return null;
    }
    
    
    
    
    private final class ReferenceField extends Variable
    {
        private final Field field;
        private final boolean _const;
        private KSLType type;
        
        private ReferenceField(Field field)
        {
            super();
            this.field = field;
            this._const = Modifier.isFinal(field.getModifiers());
        }
        
        @Override
        public final KSLType getTypeOwner() { return KSLReference.this; }
        
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
    }
    
    
    private final class ReferenceMethod extends Function
    {
        private final Signature signature;
        private final KSLType returnType;
        private final ReferenceMethodParameter[] pars;
        
        private ReferenceMethod(Signature signature, Method method) throws ClassNotFoundException
        {
            this.signature = signature;
            this.pars = new ReferenceMethodParameter[method.getParameterCount()];
            java.lang.reflect.Parameter[] jpars = method.getParameters();
            for(int i=0;i<pars.length;i++)
                this.pars[i] = new ReferenceMethodParameter(jpars[i]);
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
        public Variable getScriptOwnerInstance() { return null; }

        @Override
        public final KSLType getTypeOwner() { return KSLReference.this; }
        
    }
    
    private final class ReferenceMethodParameter extends Function.Parameter
    {
        private ReferenceMethodParameter(java.lang.reflect.Parameter jpar) throws ClassNotFoundException
        {
            super(jpar.getName(), classLoader.findKSLType(jpar.getType()), jpar.isVarArgs());
        }
    }
}
