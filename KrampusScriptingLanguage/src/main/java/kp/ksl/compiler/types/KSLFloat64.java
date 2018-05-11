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
public final class KSLFloat64 extends KSLPrimitive
{
    @Override
    final String typeid() { return Typeid.FLOAT64; }

    @Override
    public final String getName() { return "double"; }
}
