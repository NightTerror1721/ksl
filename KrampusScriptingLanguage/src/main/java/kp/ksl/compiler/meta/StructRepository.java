/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.meta;

import kp.ksl.lang.Struct;

/**
 *
 * @author Asus
 */
public interface StructRepository
{
    boolean hasStructClass(String name);
    Class<? extends Struct> getStructClass(String name);
    
    public static final StructRepository IMMUTABLE_EMPTY = new StructRepository()
    {
        @Override
        public final boolean hasStructClass(String name) { return false; }

        @Override
        public final Class<? extends Struct> getStructClass(String name) { return null; }
    };
}
