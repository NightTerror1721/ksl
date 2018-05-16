/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler;

/**
 *
 * @author Asus
 */
public enum InstructionId implements InstructionCode
{
    IF,
    ELSE,
    FOR,
    WHILE,
    DO,
    SWITCH,
    TYPEDEF;

    @Override
    public final InstructionCodeType getInstructionCodeType() { return InstructionCodeType.ID; }
    
}
