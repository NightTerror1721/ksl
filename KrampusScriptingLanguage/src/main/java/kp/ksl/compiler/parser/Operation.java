/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.parser;

import kp.ksl.compiler.exception.CompilationError;
import kp.ksl.compiler.parser.Operator.OperatorType;

/**
 *
 * @author Asus
 */
public abstract class Operation extends Statement
{
    public abstract Operator getOperator();
    
    public abstract Statement getOperand(int index);
    
    public final OperatorType getOperatorType() { return getOperator().getOperatorType(); }
    public final boolean isUnary() { return getOperator().isUnary(); }
    public final boolean isBinary() { return getOperator().isBinary(); }
    public final boolean isTernaryConditional() { return getOperator().isTernaryConditional(); }
    public final boolean isAssignment() { return getOperator().isAssignment(); }
    public final boolean isFunctionCall() { return getOperator().isFunctionCall(); }
    public final boolean isArraySubcripting() { return getOperator().isArraySubcripting(); }
    
    @Override
    public final StatementType getStatementType() { return StatementType.OPERATION; }
    
    
    public static final Operation unary(Operator operator, Statement op)
    {
        if(!operator.isUnary())
            throw new IllegalArgumentException();
        return new DefaultOperation(operator, op);
    }
    
    public static final Operation binary(Operator operator, Statement left, Statement right)
    {
        if(!operator.isBinary())
            throw new IllegalArgumentException();
        return new DefaultOperation(operator, left, right);
    }
    
    public static final Operation ternaryConditional(Statement cond, Statement trueOp, Statement falseOp)
    {
        return new DefaultOperation(Operator.TERNARY_CONDITIONAL, cond, trueOp, falseOp);
    }
    
    public static final Operation assignment(Operator operator, Statement location, Statement op)
    {
        if(!operator.isAssignment())
            throw new IllegalArgumentException();
        return new DefaultOperation(operator, location, op);
    }
    
    public static final Operation arraySubcripting(Statement array, Statement indexOp)
    {
        return new DefaultOperation(Operator.ARRAY_SUBCRIPTING, array, indexOp);
    }
    
    public static final Operation functionCall(Identifier name, Statement... args) { return FunctionCall.createCall(name, args); }
    public static final Operation functionCall(Identifier name, UnparsedStatementList argsList) throws CompilationError { return FunctionCall.createCall(name, argsList); }
    
    
    private static final class DefaultOperation extends Operation
    {
        private final Operator operator;
        private final Statement[] operands;
        
        private DefaultOperation(Operator operator, Statement... operands)
        {
            if(operator == null)
                throw new NullPointerException();
            if(operands == null)
                throw new NullPointerException();
            for(int i=0;i<operands.length;i++)
                if(operands[i] == null)
                    throw new NullPointerException();
            this.operator = operator;
            this.operands = operands;
        }

        @Override
        public final Operator getOperator() { return operator; }

        @Override
        public final Statement getOperand(int index) { return operands[index]; }
    }
}
