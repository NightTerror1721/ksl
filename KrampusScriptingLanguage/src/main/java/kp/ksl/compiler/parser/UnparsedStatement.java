/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.parser;

import kp.ksl.compiler.InstructionCode;
import kp.ksl.compiler.InstructionCodeType;

/**
 *
 * @author Asus
 */
public abstract class UnparsedStatement implements InstructionCode
{
    public abstract StatementType getStatementType();
    
    public abstract boolean isValidOperand();
    
    public final boolean is(StatementType type0, StatementType type1)
    {
        StatementType c = getStatementType();
        return c == type0 || c == type1;
    }
    public final boolean is(StatementType type0, StatementType type1, StatementType type2)
    {
        StatementType c = getStatementType();
        return c == type0 || c == type1 || c == type2;
    }
    public final boolean is(StatementType... types)
    {
        StatementType c = getStatementType();
        for(int i=0;i<types.length;i++)
            if(c == types[i])
                return true;
        return false;
    }
    
    public boolean isParsedStatement() { return false; }
    
    @Override
    public final InstructionCodeType getInstructionCodeType() { return InstructionCodeType.STATEMENT; }
}
