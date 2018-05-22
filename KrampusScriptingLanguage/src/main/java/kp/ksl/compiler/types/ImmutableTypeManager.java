/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import kp.ksl.compiler.exception.CompilationError;
import static kp.ksl.compiler.types.NativeType.*;

/**
 *
 * @author Asus
 */
public final class ImmutableTypeManager
{
    public static final KSLType getType(TypeModifier mod) throws CompilationError
    {
        switch(mod.getByteCount())
        {
            case TypeModifier.BYTES_BYTE: return mod.isUnsigned() ? UNSIGNED_BYTE_INT : SIGNED_BYTE_INT;
            case TypeModifier.BYTES_SHORT: return mod.isUnsigned() ? UNSIGNED_SHORT_INT : SIGNED_SHORT_INT;
            case TypeModifier.BYTES_LONG:
                if(mod.isUnsigned())
                    throw new CompilationError("Invalid type: " + mod);
                return SIGNED_LONG_INT;
            default: return mod.isUnsigned() ? UNSIGNED_INT : SIGNED_INT;
        }
    }
    
    public static final KSLType getType(TypeModifier mod, String name) throws CompilationError
    {
        switch(name)
        {
            case Typename.INTEGER: return getType(mod);
            case Typename.FLOAT:
                if(!mod.isUnsigned())
                {
                    if(mod.isNormal())
                        return SIGNED_FLOAT;
                    if(mod.isLong())
                        return SIGNED_LONG_FLOAT;
                }
                break;
        }
        throw new CompilationError("Invalid type: " + mod + " " + name);
    }
    
    public static final KSLType getType(String name) throws CompilationError
    {
        switch(name)
        {
            case Typename.VOID: return VOID;
            case Typename.INTEGER: return SIGNED_INT;
            case Typename.FLOAT: return SIGNED_FLOAT;
            case Typename.CHARACTER: return CHAR;
            case Typename.STRING: return STRING;
            case Typename.REFERENCE: return REF;
            default: throw new CompilationError("Invalid type: " + name);
        }
    }
}
