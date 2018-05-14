/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.lang;

/**
 *
 * @author Asus
 */
public class UnsignedByteInteger extends Unsigned
{
    private final short value;
    
    public UnsignedByteInteger(short value)
    {
        this.value = (short) (Math.abs(value) % 0xff);
    }
    
    @Override
    public final int intValue() { return value; }

    @Override
    public long longValue() { return value; }

    @Override
    public float floatValue() { return value; }

    @Override
    public double doubleValue() { return value; }

    @Override
    public int compareTo(Number o) { return Short.compare(value, o.shortValue()); }
    
    
    public static final UnsignedByteInteger ZERO = new UnsignedByteInteger((short) 0);
    public static final UnsignedByteInteger ONE = new UnsignedByteInteger((short) 1);
    
    
    public static final UnsignedByteInteger valueOf(byte value) { return new UnsignedByteInteger(value); }
    public static final UnsignedByteInteger valueOf(short value) { return new UnsignedByteInteger(value); }
    public static final UnsignedByteInteger valueOf(int value) { return new UnsignedByteInteger((short) value); }
    public static final UnsignedByteInteger valueOf(long value) { return new UnsignedByteInteger((short) value); }
    public static final UnsignedByteInteger valueOf(float value) { return new UnsignedByteInteger((short) value); }
    public static final UnsignedByteInteger valueOf(double value) { return new UnsignedByteInteger((short) value); }
    public static final UnsignedByteInteger valueOf(char value) { return new UnsignedByteInteger((short) value); }
    public static final UnsignedByteInteger valueOf(boolean value) { return new UnsignedByteInteger((short) (value ? 1 : 0)); }
    public static final UnsignedByteInteger valueOf(Number value) { return new UnsignedByteInteger(value.shortValue()); }
    public static final UnsignedByteInteger valueOf(String value) { return new UnsignedByteInteger(Short.decode(value)); }
}
