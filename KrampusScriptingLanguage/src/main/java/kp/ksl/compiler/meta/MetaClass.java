/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.meta;

import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;

public abstract class MetaClass<C>
{
    protected final Class<C> jclass;
    protected final Type jtype;
    protected final String name;
    
    MetaClass(Class<C> jclass)
    {
        if(jclass == null)
            throw new NullPointerException();
        this.jclass = jclass;
        this.jtype = new ObjectType(this.name = jclass.getName());
    }
    
    public final String getName() { return name; }
    public final Type getJavaType() { return jtype; }
    public final Class<C> getJavaClass() { return jclass; }
    
    public abstract boolean isScript();
    public abstract boolean isReference();
    
    
    public static final <C> MetaClass<C> valueOf(Class<C> jclass)
    {
        MetaClass<C> mc = MetaScript.valueOf(jclass);
        return mc == null ? MetaReference.valueOf(jclass) : mc;
    }
}
