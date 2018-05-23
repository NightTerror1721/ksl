/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.meta;

import kp.ksl.compiler.types.KSLType;

/**
 *
 * @author Asus
 */
public abstract class MetaObject
{
    MetaObject() {}
    
    public abstract KSLType getTypeOwner();
}
