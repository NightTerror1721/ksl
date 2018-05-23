/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.meta;

import kp.ksl.compiler.types.KSLReference;
import kp.ksl.compiler.types.KSLStruct;
import kp.ksl.lang.KSLClassLoader;
import kp.ksl.lang.Struct;
import org.apache.bcel.generic.Type;

public abstract class MetaClass
{
    protected final String name;
    protected final Type jtype;
    protected final Class<?> jclass;
    
    
    protected MetaClass(String name, Type jtype, Class<?> jclass)
    {
        if(name == null)
            throw new NullPointerException();
        if(jtype == null)
            throw new NullPointerException();
        if(jclass == null)
            throw new NullPointerException();
        this.name = name;
        this.jtype = jtype;
        this.jclass = jclass;
    }
    
    public final String getName() { return name; }
    public final Type getJavaType() { return jtype; }
    public final Class<?> getJavaClass() { return jclass; }
    
    public abstract boolean isScript();
    public abstract boolean isKSLType();
    
    
    public static final MetaClass valueOf(Class<?> jclass, KSLClassLoader classLoader)
    {
        if(Struct.class.isAssignableFrom(jclass))
            return KSLStruct.createStruct(jclass, classLoader);
        MetaClass mc = MetaScript.createMetaScript(jclass, classLoader);
        return mc == null ? KSLReference.createReference(jclass, classLoader) : mc;
    }
}
