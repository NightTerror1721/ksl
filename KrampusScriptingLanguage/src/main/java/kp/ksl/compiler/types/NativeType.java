/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

/**
 *
 * @author Asus
 */
public final class NativeType
{
    private NativeType() {}
    
    public static final KSLType VOID = new KSLVoid();
    
    public static final KSLType SIGNED_BYTE_INT = new KSLSignedInt8();
    public static final KSLType SIGNED_SHORT_INT = new KSLSignedInt16();
    public static final KSLType SIGNED_INT = new KSLSignedInt32();
    public static final KSLType SIGNED_LONG_INT = new KSLSignedInt64();
    
    public static final KSLType UNSIGNED_BYTE_INT = new KSLUnsignedInt8();
    public static final KSLType UNSIGNED_SHORT_INT = new KSLUnsignedInt16();
    public static final KSLType UNSIGNED_INT = new KSLUnsignedInt32();
    
    public static final KSLType SIGNED_FLOAT = new KSLFloat32();
    public static final KSLType SIGNED_LONG_FLOAT = new KSLFloat64();
    
    public static final KSLType CHAR = new KSLChar();
    public static final KSLType STRING = new KSLString();
    
    public static final KSLType REF = new KSLReference();
}
