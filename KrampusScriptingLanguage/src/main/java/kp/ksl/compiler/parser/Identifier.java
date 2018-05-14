/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.parser;

import java.util.regex.Pattern;
import kp.ksl.compiler.exception.CompilationError;

/**
 *
 * @author Asus
 */
public final class Identifier implements UnparsedStatement, Statement
{
    private final String identifier;
    
    private Identifier(String identifier)
    {
        this.identifier = identifier;
    }
    
    @Override
    public final boolean isValidOperand() { return true; }

    @Override
    public final StatementType getStatementType() { return StatementType.IDENTIFIER; }
    
    @Override
    public final String toString() { return identifier; }
    
    
    private static final Pattern ID_PAT = Pattern.compile("[_a-zA-Z][_a-zA-Z0-9]*");
    
    public static final boolean isValidIdentifier(String str)
    {
        return ID_PAT.matcher(str).matches();
    }
    
    public static final Identifier valueOf(String str) throws CompilationError
    {
        if(!isValidIdentifier(str))
            throw new CompilationError("Invalid identifier: " + str);
        return new Identifier(str);
    }
}
