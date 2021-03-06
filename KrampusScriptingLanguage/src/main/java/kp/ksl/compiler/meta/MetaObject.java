/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.meta;

import kp.ksl.compiler.meta.MetaScript.ScriptReferenceField;
import kp.ksl.compiler.types.KSLType;
import org.apache.bcel.generic.Type;

/**
 *
 * @author Asus
 */
public abstract class MetaObject
{
    MetaObject() {}
    
    public abstract KSLType getTypeOwner();
    public Type getTypeOwnerAsBcel() { return getTypeOwner().getJavaType(); }
    
    public abstract ScriptReferenceField getScriptOwnerInstance();
}
