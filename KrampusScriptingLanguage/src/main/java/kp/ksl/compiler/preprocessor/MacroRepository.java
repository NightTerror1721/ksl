/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.preprocessor;

/**
 *
 * @author Asus
 */
public interface MacroRepository
{
    boolean hasMacro(String name);
    Macro getMacro(String name);
}
