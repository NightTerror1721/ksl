/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.parser;

import kp.ksl.compiler.types.KSLType;

/**
 *
 * @author Asus
 */
public abstract class Literal implements UnparsedStatement, Statement
{
    private final KSLType type;
    
    private Literal(KSLType type) { this.type = type; }
    
    public final KSLType getType() { return type; }
    
    
}
