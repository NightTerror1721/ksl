/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler;

import java.util.LinkedList;
import kp.ksl.compiler.types.KSLType;

/**
 *
 * @author Asus
 */
public class MemStack
{
    private final LinkedList<KSLType> stack = new LinkedList<>();
    
    public final void push(KSLType type)
    {
        if(type == null)
            throw new NullPointerException();
        stack.push(type);
    }
    
    public final KSLType pop()
    {
        if(stack.isEmpty())
            throw new IllegalStateException("Mem Stack is already empty");
        return stack.pop();
    }
    
    public final KSLType peek()
    {
        if(stack.isEmpty())
            throw new IllegalStateException("Mem Stack is already empty");
        return stack.peek();
    }
    
    public final int size() { return stack.size(); }
    
    public final boolean isEmpty() { return stack.isEmpty(); }
}
