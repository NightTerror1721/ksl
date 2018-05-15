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
public final class Stopchar implements UnparsedStatement
{
    private final String symbol;
    
    private Stopchar(String symbol) { this.symbol = symbol; }
    
    @Override
    public final boolean isValidOperand() { return false; }
    
    @Override
    public final StatementType getStatementType() { return StatementType.STOPCHAR; }
    
    @Override
    public final String toString() { return symbol; }
    
    
    public static final Stopchar SEMICOLON = new Stopchar(";");
    public static final Stopchar COMMA = new Stopchar(",");
    public static final Stopchar TWO_POINTS = new Stopchar(":");
}
