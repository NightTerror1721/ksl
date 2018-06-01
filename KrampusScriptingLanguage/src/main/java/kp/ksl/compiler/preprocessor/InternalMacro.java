/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.preprocessor;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 *
 * @author Asus
 */
final class InternalMacro implements Macro
{
    private final String name;
    private final BiFunction<MacroRepository, String[], String> expandAction;
    private final boolean[] expandParameters;
    
    InternalMacro(String name, BiFunction<MacroRepository, String[], String> expandAction, boolean[] expandParameters)
    {
        this.name = Objects.requireNonNull(name);
        this.expandAction = Objects.requireNonNull(expandAction);
        this.expandParameters = Objects.requireNonNull(expandParameters);
    }
    
    @Override
    public final String getName() { return name; }
    
    @Override
    public final boolean expandParameter(int index) { return index < 0 || index >= expandParameters.length ? true : expandParameters[index]; }

    @Override
    public final String expand(MacroRepository macros, String[] args) { return expandAction.apply(macros, args); }
}
