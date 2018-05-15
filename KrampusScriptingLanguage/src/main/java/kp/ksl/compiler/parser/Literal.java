/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.parser;

import java.util.regex.Pattern;
import kp.ksl.compiler.exception.CompilationError;
import kp.ksl.compiler.types.KSLType;
import kp.ksl.compiler.types.Modifier;
import kp.ksl.compiler.types.NativeType;
import kp.ksl.lang.UnsignedByteInteger;
import kp.ksl.lang.UnsignedInteger;
import kp.ksl.lang.UnsignedShortInteger;

/**
 *
 * @author Asus
 */
public final class Literal implements UnparsedStatement, Statement
{
    private final LiteralType type;
    private final Object value;
    
    private Literal(LiteralType type, Object value)
    {
        this.type = type;
        this.value = value;
    }
    
    public final LiteralType getLiteralType() { return type; }
    
    public final KSLType getType() { return type.type; }
    
    public final byte sint8() { return (byte) value; }
    public final short sint16() { return (short) value; }
    public final int sint32() { return (int) value; }
    public final long sint64() { return (long) value; }
    public final UnsignedByteInteger uint8() { return (UnsignedByteInteger) value; }
    public final UnsignedShortInteger uint16() { return (UnsignedShortInteger) value; }
    public final UnsignedInteger uint32() { return (UnsignedInteger) value; }
    public final float float32() { return (float) value; }
    public final double float64() { return (double) value; }
    public final char character() { return (char) value; }
    public final String string() { return (String) value; }

    @Override
    public final boolean isValidOperand() { return true; }

    @Override
    public final StatementType getStatementType() { return StatementType.LITERAL; }
    
    public static final Literal sint8(byte value) { return new Literal(LiteralType.SINT8, value); }
    public static final Literal sint16(short value) { return new Literal(LiteralType.SINT16, value); }
    public static final Literal sint32(int value) { return new Literal(LiteralType.SINT32, value); }
    public static final Literal sint64(long value) { return new Literal(LiteralType.SINT64, value); }
    
    public static final Literal uint8(short value) { return new Literal(LiteralType.UINT8, new UnsignedByteInteger(value)); }
    public static final Literal uint8(UnsignedByteInteger value) { return new Literal(LiteralType.UINT8, value); }
    public static final Literal uint16(int value) { return new Literal(LiteralType.UINT16, new UnsignedShortInteger(value)); }
    public static final Literal uint16(UnsignedShortInteger value) { return new Literal(LiteralType.UINT16, value); }
    public static final Literal uint32(long value) { return new Literal(LiteralType.UINT32, new UnsignedInteger(value)); }
    public static final Literal uint32(UnsignedInteger value) { return new Literal(LiteralType.UINT32, value); }
    
    public static final Literal float32(float value) { return new Literal(LiteralType.FLOAT32, value); }
    public static final Literal float64(double value) { return new Literal(LiteralType.FLOAT64, value); }
    
    public static final Literal character(char value) { return new Literal(LiteralType.CHAR, value); }
    public static final Literal string(String value) { return new Literal(LiteralType.STRING, value); }
    
    
    private static final Pattern INTEGER_P = Pattern.compile("(0|0[xX])?[0-9]+[bBsSlL]?[uU]?");
    private static final Pattern FLOAT_P = Pattern.compile("[0-9]+(\\.[0-9]+)?[fFdD]?");
    
    
    public static final Literal decodeNumber(String str) throws CompilationError
    {
        if(INTEGER_P.matcher(str).matches())
            return decodeInteger(str);
        if(FLOAT_P.matcher(str).matches())
            return decodeFloat(str);
        return null;
    }
    
    private static boolean isUnsigned(String str)
    {
        char c = str.charAt(str.length() - 1);
        return c == 'u' || c == 'U';
    }
    private static int base(String str)
    {
        if(str.length() <= 1)
            return 10;
        char c = str.charAt(0);
        if(c != '0')
            return 10;
        c = str.charAt(1);
        return c == 'x' || c == 'X' ? 16 : 8;
    }
    private static int integerLen(String str, boolean unsigned)
    {
        char c = str.charAt(str.length() - (unsigned ? -2 : -1));
        switch(c)
        {
            case 'b': case 'B': return Modifier.BYTES_BYTE;
            case 's': case 'S': return Modifier.BYTES_SHORT;
            case 'l': case 'L': return Modifier.BYTES_LONG;
            default: return Modifier.BYTES_NORMAL;
        }
    }
    private static Literal decodeInteger(String str) throws CompilationError
    {
        boolean unsigned = isUnsigned(str);
        int base = base(str);
        int bytes = integerLen(str, unsigned);
        int start = base == 8 ? 1 : base == 16 ? 2 : 0;
        int end = str.length() - ((unsigned ? 1 : 0) + (bytes != Modifier.BYTES_NORMAL ? 1 : 0));
        str = str.substring(start, end);
        
        try
        {
            switch(bytes)
            {
                case Modifier.BYTES_BYTE: return unsigned
                        ? sint8(Byte.parseByte(str, base))
                        : uint8(Short.parseShort(str, base));
                case Modifier.BYTES_SHORT: return unsigned
                        ? sint16(Short.parseShort(str, base))
                        : uint16(Integer.parseInt(str, base));
                case Modifier.BYTES_LONG: return sint64(Long.parseLong(str, base));
                default: return unsigned
                        ? sint32(Integer.parseInt(str, base))
                        : uint32(Long.parseLong(str, base));
            }
        }
        catch(NumberFormatException ex)
        {
            throw new CompilationError("Invalid Integer literal: " + ex);
        }
    }
    
    private static Literal decodeFloat(String str)
    {
        char c = str.charAt(str.length() -1);
        boolean large;
        
        switch(c)
        {
            case 'd': case 'D':
                large = true;
                str = str.substring(0, str.length() - 1);
                break;
            case 'f': case 'F':
                str = str.substring(0, str.length() - 1);
            default: large = false; break;
        }
        if(large)
            return float64(Double.parseDouble(str));
        return float32(Float.parseFloat(str));
    }
    
    
    public enum LiteralType
    {
        SINT8(NativeType.SIGNED_BYTE_INT),
        SINT16(NativeType.SIGNED_SHORT_INT),
        SINT32(NativeType.SIGNED_INT),
        SINT64(NativeType.SIGNED_LONG_INT),
        UINT8(NativeType.UNSIGNED_BYTE_INT),
        UINT16(NativeType.UNSIGNED_SHORT_INT),
        UINT32(NativeType.UNSIGNED_INT),
        FLOAT32(NativeType.SIGNED_FLOAT),
        FLOAT64(NativeType.SIGNED_LONG_FLOAT),
        CHAR(NativeType.CHAR),
        STRING(NativeType.STRING);
        
        private final KSLType type;
        
        private LiteralType(KSLType type)
        {
            this.type = type;
        }
    }
}
