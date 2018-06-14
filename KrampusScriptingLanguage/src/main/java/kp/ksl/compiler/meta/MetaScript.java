/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.meta;

import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;

/**
 *
 * @author Asus
 */
public abstract class MetaScript extends MetaClass
{
    protected MetaScript(Class<?> jclass)
    {
        super(jclass.getName(), new ObjectType(jclass.getName()), jclass);
    }
    
    public abstract class ScriptReferenceField
    {
        public abstract String getName();
        public abstract Type getBcelType();
    }
}
