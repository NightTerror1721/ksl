/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.parser;

/**
 *
 * @author Asus
 */
public abstract class Operator extends UnparsedStatement
{
    private final String symbol;
    private final int priority;
    
    private Operator(String symbol, int priority)
    {
        this.symbol = symbol;
        this.priority = priority;
    }
    
    public final String getSymbol() { return symbol; }
    public final int getPriority() { return priority; }
    
    public boolean hasInnerOperator() { return false; }
    public Operator getInnerOperator() { return null; }
    
    public boolean hasRightToLeftOrder() { return false; }
    
    public abstract OperatorType getOperatorType();
    public final boolean isUnary() { return getOperatorType() == OperatorType.UNARY; }
    public final boolean isBinary() { return getOperatorType() == OperatorType.BINARY; }
    public final boolean isTernaryConditional() { return getOperatorType() == OperatorType.TERNARY_CONDITIONAL; }
    public final boolean isAssignment() { return getOperatorType() == OperatorType.ASSIGNMENT; }
    public final boolean isFunctionCall() { return getOperatorType() == OperatorType.FUNCTION_CALL; }
    public final boolean isArraySubcripting() { return getOperatorType() == OperatorType.ARRAY_SUBCRIPTING; }
    
    public final int comparePriority(Operator op)
    {
        if(priority == op.priority)
            return hasRightToLeftOrder() || op.hasRightToLeftOrder() ? -1 : 0;
        return priority < op.priority ? 1 : -1;
    }
    
    @Override
    public final boolean isValidOperand() { return false; }

    @Override
    public final StatementType getStatementType() { return StatementType.OPERATOR; }
    
    
    public static final Operator
            SUFIX_INCREMENT = new ReversibleUnaryOperator("++", 1, false),
            SUFIX_DECREMENT = new ReversibleUnaryOperator("--", 1, false),
            FUNCTION_CALL = new FunctionCallOperator(1),
            ARRAY_SUBCRIPTING = new ArraySubcriptingOperator(1),
            MEMBER_ACCESS = new BinaryOperator(".", 1),
            
            PREFIX_INCREMENT = new ReversibleUnaryOperator("++", 2, true),
            PREFIX_DECREMENT = new ReversibleUnaryOperator("--", 2, true),
            UNARY_PLUS = new UnaryOperator("+", 2),
            UNARY_MINUS = new UnaryOperator("-", 2),
            NOT = new UnaryOperator("!", 2),
            BITWISE_NOT = new UnaryOperator("~", 2),
            TYPE_CAST = new UnaryOperator("(type)", 2),
            ARRAY_LENGTH = new UnaryOperator("lengthof", 2),
            
            MULTIPLICATION = new BinaryOperator("*", 3),
            DIVISION = new BinaryOperator("/", 3),
            REMAINDER = new BinaryOperator("%", 3),
            
            ADDITION = new BinaryOperator("+", 4),
            SUBTRACTION = new BinaryOperator("-", 4),
            
            BITWISE_LEFT_SHIFT = new BinaryOperator("<<", 5),
            BITWISE_RIGHT_SHIFT = new BinaryOperator(">>", 5),
            
            GREATER_THAN = new BinaryOperator(">", 6),
            LESS_THAN = new BinaryOperator("<", 6),
            GREATER_EQUAL_THAN = new BinaryOperator(">=", 6),
            LESS_EQUAL_THAN = new BinaryOperator("<=", 6),
            
            EQUALS = new BinaryOperator("==", 7),
            NOT_EQUALS = new BinaryOperator("!=", 7),
            
            BITWISE_AND = new BinaryOperator("&", 8),
            
            BITWISE_XOR = new BinaryOperator("^", 9),
            
            BITWISE_OR = new BinaryOperator("|", 10),
            
            LOGICAL_AND = new BinaryOperator("&&", 11),
            
            LOGICAL_OR = new BinaryOperator("||", 12),
            
            TERNARY_CONDITIONAL = new TernaryConditionalOperator(13),
            
            ASSIGNMENT = new AssignmentOperator("=", 14),
            ASSIGNMENT_ADDITION = new AssignmentOperator("+=", 14),
            ASSIGNMENT_SUBTRACTION = new AssignmentOperator("-=", 14),
            ASSIGNMENT_MULTIPLICATION = new AssignmentOperator("*=", 14),
            ASSIGNMENT_DIVISION = new AssignmentOperator("/=", 14),
            ASSIGNMENT_BITWISE_LEFT_SHIFT = new AssignmentOperator("<<=", 14),
            ASSIGNMENT_BITWISE_RIGHT_SHIFT = new AssignmentOperator(">>=", 14),
            ASSIGNMENT_BITWISE_AND = new AssignmentOperator("&=", 14),
            ASSIGNMENT_BITWISE_XOR = new AssignmentOperator("^=", 14),
            ASSIGNMENT_BITWISE_OR = new AssignmentOperator("|=", 14);
            
    
    
    private static class UnaryOperator extends Operator
    {
        public UnaryOperator(String symbol, int priority)
        {
            super(symbol, priority);
        }
        
        @Override
        public final OperatorType getOperatorType() { return OperatorType.UNARY; }
        
        @Override
        public boolean hasRightToLeftOrder() { return true; }
    }
    
    private static class ReversibleUnaryOperator extends UnaryOperator
    {
        private final boolean rightToLeft;
        
        public ReversibleUnaryOperator(String symbol, int priority, boolean rightToLeft)
        {
            super(symbol, priority);
            this.rightToLeft = rightToLeft;
        }
        
        @Override
        public final boolean hasRightToLeftOrder() { return rightToLeft; }
    }
    
    private static class BinaryOperator extends Operator
    {
        public BinaryOperator(String symbol, int priority)
        {
            super(symbol, priority);
        }
        
        @Override
        public final OperatorType getOperatorType() { return OperatorType.BINARY; }
    }
    
    private static class TernaryConditionalOperator extends Operator
    {
        public TernaryConditionalOperator(int priority)
        {
            super("?:", priority);
        }
        
        @Override
        public final OperatorType getOperatorType() { return OperatorType.TERNARY_CONDITIONAL; }
    }
    
    private static class AssignmentOperator extends Operator
    {
        private final Operator innerOperator;
        
        public AssignmentOperator(String symbol, int priority, Operator innerOperator)
        {
            super(symbol, priority);
            this.innerOperator = innerOperator;
        }
        public AssignmentOperator(String symbol, int priority) { this(symbol, priority, null); }
        
        @Override
        public final OperatorType getOperatorType() { return OperatorType.ASSIGNMENT; }
        
        @Override
        public final boolean hasInnerOperator() { return innerOperator != null; }
        
        @Override
        public final Operator getInnerOperator() { return innerOperator; }
    }
    
    private static class FunctionCallOperator extends Operator
    {
        public FunctionCallOperator(int priority)
        {
            super("()", priority);
        }
        
        @Override
        public final OperatorType getOperatorType() { return OperatorType.FUNCTION_CALL; }
    }
    
    private static class ArraySubcriptingOperator extends Operator
    {
        public ArraySubcriptingOperator(int priority)
        {
            super("[]", priority);
        }
        
        @Override
        public final OperatorType getOperatorType() { return OperatorType.ARRAY_SUBCRIPTING; }
    }
    
    public enum OperatorType { UNARY, BINARY, TERNARY_CONDITIONAL, ASSIGNMENT, FUNCTION_CALL, ARRAY_SUBCRIPTING }
}
