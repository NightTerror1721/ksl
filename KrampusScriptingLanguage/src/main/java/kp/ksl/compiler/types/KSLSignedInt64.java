/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

/**
 *
 * @author Asus
 */
public final class KSLSignedInt64 extends KSLPrimitive
{
    @Override
    final String typeid() { return Typeid.SINT64; }

    @Override
    public final String getName() { return "signed long int"; }
}
