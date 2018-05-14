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
public final class UnsignedInteger extends Unsigned
{
    private final long value;
    
    public UnsignedInteger(long value)
    {
        this.value = Math.abs(value) % 0xffff;
    }
    
    @Override
    public final int intValue() { return (int) value; }

    @Override
    public long longValue() { return value; }

    @Override
    public float floatValue() { return value; }

    @Override
    public double doubleValue() { return value; }

    @Override
    public int compareTo(Number o) { return Long.compare(value, o.longValue()); }
    
    
    public static final UnsignedInteger ZERO = new UnsignedInteger(0L);
    public static final UnsignedInteger ONE = new UnsignedInteger(1L);
    
    
    public static final UnsignedInteger valueOf(byte value) { return new UnsignedInteger(value); }
    public static final UnsignedInteger valueOf(short value) { return new UnsignedInteger(value); }
    public static final UnsignedInteger valueOf(int value) { return new UnsignedInteger(value); }
    public static final UnsignedInteger valueOf(long value) { return new UnsignedInteger(value); }
    public static final UnsignedInteger valueOf(float value) { return new UnsignedInteger((long) value); }
    public static final UnsignedInteger valueOf(double value) { return new UnsignedInteger((long) value); }
    public static final UnsignedInteger valueOf(char value) { return new UnsignedInteger((long) value); }
    public static final UnsignedInteger valueOf(boolean value) { return new UnsignedInteger(value ? 1L : 0L); }
    public static final UnsignedInteger valueOf(Number value) { return new UnsignedInteger(value.longValue()); }
    public static final UnsignedInteger valueOf(String value) { return new UnsignedInteger(Long.decode(value)); }
}
