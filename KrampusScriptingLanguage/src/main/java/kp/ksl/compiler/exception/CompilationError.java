/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.exception;

/**
 *
 * @author Asus
 */
public final class CompilationError extends Exception
{
    public CompilationError(String errorMessage) { super(errorMessage); }
}
