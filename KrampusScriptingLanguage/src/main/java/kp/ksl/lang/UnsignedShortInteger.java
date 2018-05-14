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
public final class UnsignedShortInteger extends Unsigned
{
    private final int value;
    
    public UnsignedShortInteger(int value)
    {
        this.value = Math.abs(value) % 0xffff;
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
    public int compareTo(Number o) { return Integer.compare(value, o.intValue()); }
    
    
    public static final UnsignedShortInteger ZERO = new UnsignedShortInteger(0);
    public static final UnsignedShortInteger ONE = new UnsignedShortInteger(1);
    
    
    public static final UnsignedShortInteger valueOf(byte value) { return new UnsignedShortInteger(value); }
    public static final UnsignedShortInteger valueOf(short value) { return new UnsignedShortInteger(value); }
    public static final UnsignedShortInteger valueOf(int value) { return new UnsignedShortInteger(value); }
    public static final UnsignedShortInteger valueOf(long value) { return new UnsignedShortInteger((int) value); }
    public static final UnsignedShortInteger valueOf(float value) { return new UnsignedShortInteger((int) value); }
    public static final UnsignedShortInteger valueOf(double value) { return new UnsignedShortInteger((int) value); }
    public static final UnsignedShortInteger valueOf(char value) { return new UnsignedShortInteger((int) value); }
    public static final UnsignedShortInteger valueOf(boolean value) { return new UnsignedShortInteger(value ? 1 : 0); }
    public static final UnsignedShortInteger valueOf(Number value) { return new UnsignedShortInteger(value.intValue()); }
    public static final UnsignedShortInteger valueOf(String value) { return new UnsignedShortInteger(Integer.decode(value)); }
}
