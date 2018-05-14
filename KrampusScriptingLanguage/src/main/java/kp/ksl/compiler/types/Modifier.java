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
public final class Modifier
{
    private final int id;
    
    private Modifier(int id) { this.id = id; }
    
    public final boolean isUnsigned() { return (id & ID_UNSIGNED) == ID_UNSIGNED; }
    public final boolean isByte() { return (id & ID_BYTE) == ID_BYTE; }
    public final boolean isShort() { return (id & ID_SHORT) == ID_SHORT; }
    public final boolean isNormal() { return (id & (ID_BYTE | ID_SHORT | ID_LONG)) == 0; }
    public final boolean isLong() { return (id & ID_LONG) == ID_LONG; }
    
    private static final String STR_SIGNED = "signed";
    private static final String STR_UNSIGNED = "unsigned";
    private static final String STR_BYTE = "byte";
    private static final String STR_SHORT = "short";
    private static final String STR_LONG = "long";
    
    private static final int ID_SIGNED = 0x1;
    private static final int ID_UNSIGNED = 0x2;
    private static final int ID_BYTE = 0x4;
    private static final int ID_SHORT = 0x8;
    private static final int ID_LONG = 0x10;
    private static final int ID_SIGNED_BYTE = ID_SIGNED | ID_BYTE;
    private static final int ID_SIGNED_SHORT = ID_SIGNED | ID_SHORT;
    private static final int ID_SIGNED_LONG = ID_SIGNED | ID_LONG;
    private static final int ID_UNSIGNED_BYTE = ID_UNSIGNED | ID_BYTE;
    private static final int ID_UNSIGNED_SHORT = ID_UNSIGNED | ID_SHORT;
    
    public static final int BYTES_BYTE = 1;
    public static final int BYTES_SHORT = 2;
    public static final int BYTES_NORMAL = 4;
    public static final int BYTES_LONG = 8;
    
    
    public static final Modifier SIGNED = new Modifier(ID_SIGNED);
    public static final Modifier UNSIGNED = new Modifier(ID_UNSIGNED);
    public static final Modifier BYTE = new Modifier(ID_BYTE);
    public static final Modifier SHORT = new Modifier(ID_SHORT);
    public static final Modifier LONG = new Modifier(ID_LONG);
    public static final Modifier SIGNED_BYTE = new Modifier(ID_SIGNED_BYTE);
    public static final Modifier SIGNED_SHORT = new Modifier(ID_SIGNED_SHORT);
    public static final Modifier SIGNED_LONG = new Modifier(ID_SIGNED_LONG);
    public static final Modifier UNSIGNED_BYTE = new Modifier(ID_UNSIGNED_BYTE);
    public static final Modifier UNSIGNED_SHORT = new Modifier(ID_UNSIGNED_SHORT);
    
    public static final boolean isValidModifier(String str)
    {
        switch(str)
        {
            case STR_SIGNED:
            case STR_UNSIGNED:
            case STR_BYTE:
            case STR_SHORT:
            case STR_LONG:
                return true;
            default: return false;
        }
    }
    
    public static final Modifier valueOf(String str) throws CompilationError
    {
        switch(str)
        {
            case STR_SIGNED: return SIGNED;
            case STR_UNSIGNED: return UNSIGNED;
            case STR_BYTE: return BYTE;
            case STR_SHORT: return SHORT;
            case STR_LONG: return LONG;
            default: throw new CompilationError("Invalid Modifier: " + str);
        }
    }
    
    public static final Modifier join(Modifier m1, Modifier m2) throws CompilationError
    {
        switch(m1.id)
        {
            case ID_SIGNED: switch(m2.id) {
                case ID_BYTE: return SIGNED_BYTE;
                case ID_SHORT: return SIGNED_SHORT;
                case ID_LONG: return SIGNED_LONG;
            }
            case ID_UNSIGNED: switch(m2.id) {
                case ID_BYTE: return UNSIGNED_BYTE;
                case ID_SHORT: return UNSIGNED_SHORT;
            }
            case ID_BYTE: switch(m2.id) {
                case ID_SIGNED: return SIGNED_BYTE;
                case ID_UNSIGNED: return UNSIGNED_BYTE;
            }
            case ID_SHORT: switch(m2.id) {
                case ID_SIGNED: return SIGNED_SHORT;
                case ID_UNSIGNED: return UNSIGNED_SHORT;
            }
            case ID_LONG: switch(m2.id) {
                case ID_SIGNED: return SIGNED_LONG;
            }
        }
        throw new CompilationError("Invalid modifier combination: <" + m1 + ">, <" + m2 + ">");
    }
    
    public final int getByteCount()
    {
        switch(id & ~(ID_SIGNED | ID_UNSIGNED))
        {
            case ID_BYTE: return BYTES_BYTE;
            case ID_SHORT: return BYTES_SHORT;
            case ID_LONG: return BYTES_LONG;
            default: return BYTES_NORMAL;
        }
    }
    
    @Override
    public final String toString()
    {
        switch(id)
        {
            case ID_SIGNED: return STR_SHORT;
            case ID_UNSIGNED: return STR_UNSIGNED;
            case ID_BYTE: return STR_BYTE;
            case ID_SHORT: return STR_SHORT;
            case ID_LONG: return STR_LONG;
            case ID_SIGNED_BYTE: return STR_SIGNED + " " + STR_BYTE;
            case ID_SIGNED_SHORT: return STR_SIGNED + " " + STR_SHORT;
            case ID_SIGNED_LONG: return STR_SIGNED + " " + STR_LONG;
            case ID_UNSIGNED_BYTE: return STR_UNSIGNED + " " + STR_BYTE;
            case ID_UNSIGNED_SHORT: return STR_UNSIGNED + " " + STR_SHORT;
            default: return "<undefined-modifier>";
        }
    }
}
