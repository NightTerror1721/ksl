/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import kp.ksl.compiler.exception.CompilationError;

/**
 *
 * @author Asus
 */
public final class ImmutableTypeManager
{
    private static final KSLType SIGNED_BYTE_INT = new KSLSignedInt8();
    private static final KSLType SIGNED_SHORT_INT = new KSLSignedInt16();
    private static final KSLType SIGNED_INT = new KSLSignedInt32();
    private static final KSLType SIGNED_LONG_INT = new KSLSignedInt64();
    
    private static final KSLType UNSIGNED_BYTE_INT = new KSLUnsignedInt8();
    private static final KSLType UNSIGNED_SHORT_INT = new KSLUnsignedInt16();
    private static final KSLType UNSIGNED_INT = new KSLUnsignedInt32();
    
    private static final KSLType SIGNED_FLOAT = new KSLFloat32();
    private static final KSLType SIGNED_LONG_FLOAT = new KSLFloat64();
    
    private static final KSLType CHAR = new KSLChar();
    private static final KSLType STRING = new KSLString();
    
    private static final KSLType REF = new KSLReference();
    
    public static final KSLType getType(Modifier mod) throws CompilationError
    {
        switch(mod.getByteCount())
        {
            case Modifier.BYTES_BYTE: return mod.isUnsigned() ? UNSIGNED_BYTE_INT : SIGNED_BYTE_INT;
            case Modifier.BYTES_SHORT: return mod.isUnsigned() ? UNSIGNED_SHORT_INT : SIGNED_SHORT_INT;
            case Modifier.BYTES_LONG:
                if(mod.isUnsigned())
                    throw new CompilationError("Invalid type: " + mod);
                return SIGNED_LONG_INT;
            default: return mod.isUnsigned() ? UNSIGNED_INT : SIGNED_INT;
        }
    }
    
    public static final KSLType getType(Modifier mod, String name) throws CompilationError
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
            case Typename.INTEGER: return SIGNED_INT;
            case Typename.FLOAT: return SIGNED_FLOAT;
            case Typename.CHARACTER: return CHAR;
            case Typename.STRING: return STRING;
            case Typename.REFERENCE: return REF;
            default: throw new CompilationError("Invalid type: " + name);
        }
    }
}
