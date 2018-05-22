/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.meta;

/**
 *
 * @author Asus
 */
public class MetaReference<C> extends MetaClass<C>
{

    private MetaReference(Class<C> jclass)
    {
        super(jclass);
    }
    
    
    public static final <C> MetaReference<C> createMetaReference(Class<C> jclass)
    {
        return null;
    }
    
    @Override
    public final boolean isScript() { return false; }

    @Override
    public final boolean isReference() { return true; }
    
}
