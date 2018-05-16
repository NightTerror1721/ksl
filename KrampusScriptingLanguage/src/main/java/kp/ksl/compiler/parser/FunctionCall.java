/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.parser;

import kp.ksl.compiler.exception.CompilationError;

/**
 *
 * @author Asus
 */
public final class FunctionCall extends Operation
{
    private final Identifier identifier;
    private final Statement[] arguments;
    
    private FunctionCall(Identifier identifier, Statement[] arguments)
    {
        this.identifier = identifier;
        this.arguments = arguments;
    }
    
    public static final FunctionCall createCall(Identifier name, Statement... args)
    {
        if(name == null)
            throw new NullPointerException();
        if(args == null)
            throw new NullPointerException();
        return new FunctionCall(name, args);
    }
    
    public static final FunctionCall createCall(Identifier name, UnparsedStatementList argsList) throws CompilationError
    {
        if(argsList.isEmpty())
            return createCall(name);
        UnparsedStatementList[] uargs = argsList.split(Stopchar.COMMA);
        Statement[] args = new Statement[uargs.length];
        for(int i = 0; i < args.length; i++)
            args[i] = StatementParser.parse(uargs[i]);
        return createCall(name, args);
    }
    
    public final Identifier getName() { return identifier; }
    
    public final int getArgumentsCount() { return arguments.length; }
    public final Statement gerArgument(int index) { return arguments[index]; }

    @Override
    public Operator getOperator() { return Operator.FUNCTION_CALL; }

    @Override
    public Statement getOperand(int index)
    {
        return index == 0 ? identifier : arguments[index - 1];
    }
    
}
