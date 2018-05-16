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
@FunctionalInterface
public interface InstructionCode
{
    InstructionCodeType getInstructionCodeType();
    
    
    public static final InstructionCode END_CODE = () -> InstructionCodeType.END;
}
