/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import java.lang.reflect.Method;
import java.util.HashMap;
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
    private final HashMap<String, Variable> fields = new HashMap<>();
    private final HashMap<Signature, Function> funcs = new HashMap<>();
    
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
    public final boolean canCastTo(KSLType type) { return is(type); }
    
    
    private Function findFunction(Signature signature)
    {
        Function f = funcs.get(signature);
        if(f != null)
            return f;
        try
        {
            Class<?>[] jtypes = signature.getJavaParameterTypes();
            Method m = jclass.getMethod(signature.getName(), jtypes);
            if(m == null)
                return null;
            
        }
        catch(Exception ex) { return null; }
    }
    
    @Override
    public final boolean isValidFunction(Signature signature) { throw new UnsupportedOperationException(); }
    
    @Override
    public final Function getFunction(Signature signature) { throw new UnsupportedOperationException(); }
    
    
    public static final KSLReference createReference(Class<?> javaClass, KSLClassLoader loader)
    {
        return null;
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
        public String getScriptOwnerInstance() { return null; }

        @Override
        public final KSLType getTypeOwner() { return KSLReference.this; }
        
    }
    
    private final class ReferenceMethodParameter extends Function.Parameter
    {
        private final String name;
        private final KSLType type;
        private final boolean varargs;
        
        private ReferenceMethodParameter(java.lang.reflect.Parameter jpar) throws ClassNotFoundException
        {
            this.name = jpar.getName();
            this.type = classLoader.findKSLType(jpar.getType());
        }
        
        @Override
        public String getName() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public KSLType getType() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isVarargs() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
}
