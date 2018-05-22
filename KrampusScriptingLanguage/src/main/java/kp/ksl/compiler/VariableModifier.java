/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler;

import kp.ksl.compiler.exception.CompilationError;

/**
 *
 * @author Asus
 */
public final class VariableModifier implements InstructionCode
{
    private final int id;
    
    private VariableModifier(int id) { this.id = id; }
    
    public final boolean isConst() { return (id & ID_CONST) == ID_CONST; }
    public final boolean isStatic() { return (id & ID_STATIC) == ID_STATIC; }
    
    private static final String STR_CONST = "const";
    private static final String STR_STATIC = "static";
    
    private static final int ID_CONST = 0x1;
    private static final int ID_STATIC = 0x2;
    
    
    public static final VariableModifier CONST = new VariableModifier(ID_CONST);
    public static final VariableModifier STATIIC = new VariableModifier(ID_STATIC);
    
    public static final boolean isValidModifier(String str)
    {
        switch(str)
        {
            case STR_CONST:
            case STR_STATIC:
                return true;
            default: return false;
        }
    }
    
    public static final VariableModifier valueOf(String str) throws CompilationError
    {
        switch(str)
        {
            case STR_CONST: return CONST;
            case STR_STATIC: return STATIIC;
            default: throw new CompilationError("Invalid Modifier: " + str);
        }
    }
    
    public static final VariableModifier join(VariableModifier m1, VariableModifier m2) throws CompilationError
    {
        throw new CompilationError("Invalid modifier combination: <" + m1 + ">, <" + m2 + ">");
    }
    
    @Override
    public final String toString()
    {
        switch(id)
        {
            case ID_CONST: return STR_CONST;
            case ID_STATIC: return STR_STATIC;
            default: return "<undefined-modifier>";
        }
    }

    @Override
    public final InstructionCodeType getInstructionCodeType() { return InstructionCodeType.VARIABLE_MODIFIER; }
}
