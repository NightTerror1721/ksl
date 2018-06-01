/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.preprocessor;

import java.util.function.BiFunction;

/**
 *
 * @author Asus
 */
public interface Macro
{
    String getName();
    boolean expandParameter(int index);
    String expand(MacroRepository repository, String... parameters);
    
    
    public static Macro createInternalMacro(String name, BiFunction<MacroRepository, String[], String> expandAction, boolean... expandParameters)
    {
        return new InternalMacro(name, expandAction, expandParameters);
    }
}
