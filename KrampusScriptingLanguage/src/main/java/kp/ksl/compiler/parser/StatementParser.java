/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.parser;

import kp.ksl.compiler.exception.CompilationError;
import kp.ksl.compiler.parser.UnparsedStatementList.Pointer;

/**
 *
 * @author Asus
 */
public final class StatementParser
{
    private StatementParser() {}
    
    public static final Statement parse(UnparsedStatementList list) throws CompilationError
    {
        Pointer it = list.counter();
        Statement operand = packPart(it);
        if(it.end())
            return operand;
        return packOperation(it, operand);
    }
    
    private static Statement packPart(Pointer it) throws CompilationError
    {
        if(it.end())
            throw new CompilationError("unexpected ned of instruction");
        return packPostUnary(it, packPreUnary(it));
    }
    
    private static Statement packPreUnary(Pointer it) throws CompilationError
    {
        UnparsedStatement part = it.listValue();
        it.increase();
        if(part.is(StatementType.OPERATOR))
        {
            if(it.end())
                throw new CompilationError("unexpected end of instruction");
            Operator prefix = (Operator) part;
            if(!prefix.isUnary())
                throw new CompilationError("Operator " + prefix + " cannot be a non unary prefix operator");
            part = packNextOperatorPart(it, (Operator) part);
            if(!part.isValidOperand())
                throw new CompilationError("Expected valid operand. But found: " + part);
            return Operation.unary(prefix, (Statement) part);
        }
        if(!part.isParsedStatement())
            throw new CompilationError("Expected valid operand. But found: " + part);
        return (Statement) part;
    }
    
    private static Statement packPostUnary(Pointer it, Statement pre) throws CompilationError
    {
        if(it.end())
            return pre;
        UnparsedStatement part = it.listValue();
        
        if(part.is(StatementType.OPERATOR))
        {
            Operator sufix = (Operator) part;
            if(!sufix.isUnary())
                return pre;
            it.increase();
            if(sufix.hasRightToLeftOrder())
                throw new CompilationError("Operator " + sufix + " cannot be an unary sufix operator");
            if(!pre.isValidOperand())
                throw new CompilationError("Expected valid operand. But found: " + part);
            return packPostUnary(it, Operation.unary(sufix, pre));
        }
        return pre;
    }
    
    private static Operator findNextOperatorSymbol(UnparsedStatementList list, int index)
    {
        int len = list.length();
        for(int i=index;i<len;i++)
            if(list.get(index).is(StatementType.OPERATOR))
                return list.get(index);
        return null;
    }
    
    private static Statement getSuperOperatorScope(Pointer it, Operator opBase) throws CompilationError
    {
        int start = it.value();
        for(;!it.end();it.increase())
        {
            if(!it.listValue().is(StatementType.OPERATOR))
                continue;
            Operator op = it.listValue();
            if(opBase.comparePriority(op) > 0)
            {
                //it.decrease();
                return parse(it.list().subList(start, it.value() - start));
            }
        }
        return parse(it.list().subList(start));
    }
    
    private static Statement packOperation(Pointer it, Statement operand1) throws CompilationError
    {
        if(!it.listValue().is(StatementType.OPERATOR))
            throw new CompilationError("Expected a valid operator between operands. \"" + it.listValue() + "\"");
        Operator operator = (Operator) it.listValue();
        it.increase();
        Operation operation;
        
        if(operator.isTernaryConditional())
        {
            int start = it.value();
            int terOp = 0;
            for(;!it.end();it.increase())
            {
                UnparsedStatement c = it.listValue();
                if(c == Operator.TERNARY_CONDITIONAL)
                    terOp++;
                else if(c == Stopchar.TWO_POINTS)
                {
                    if(terOp == 0)
                        break;
                    terOp--;
                }
            }
            if(it.end())
                throw new CompilationError("Expected a : in ternary operator");
            Statement response1 = parse(it.list().subList(start, it.value()  - start));
            it.increase();
            Statement response2 = parse(it.list().subList(it.value()));
            it.finish();
            return Operation.ternaryConditional(operand1, response1, response2);
        }
        else if(operator.isBinary())
        {
            Statement operand2 = packNextOperatorPart(it, operator);
            operation = Operation.binary(operator, operand1, operand2);
        }
        else throw new CompilationError("Invalid operator type: " + operator);
        
        
        if(it.end())
            return operation;
        return packOperation(it, operation);
    }
    
    private static Statement packNextOperatorPart(Pointer it, Operator operator) throws CompilationError
    {
        Operator nextOperator = findNextOperatorSymbol(it.list(), it.value());
        if(nextOperator != null && operator.comparePriority(nextOperator) >= 0)
            nextOperator = null;

        Statement operand2;
        if(nextOperator != null)
            operand2 = getSuperOperatorScope(it, operator);
        else operand2 = packPart(it);
        
        if(operator == Operator.MEMBER_ACCESS &&
                !operand2.is(StatementType.IDENTIFIER))
            throw new CompilationError("Expected a valid identifier in PropertyAccess operator: " + operand2);
        return operand2;
    }
}
