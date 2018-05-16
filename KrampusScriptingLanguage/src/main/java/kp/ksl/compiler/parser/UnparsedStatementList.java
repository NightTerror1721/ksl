/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import kp.ksl.compiler.exception.CompilationError;

/**
 *
 * @author Asus
 */
public final class UnparsedStatementList
{
    private final UnparsedStatement[] code;
    private final int sourceLine;
    
    public UnparsedStatementList(int sourceLine, UnparsedStatement... code) { this.code = check(code); this.sourceLine = sourceLine; }
    public UnparsedStatementList(int sourceLine, Collection<? extends UnparsedStatement> c) { this(sourceLine, c.toArray(new UnparsedStatement[c.size()])); }
    public UnparsedStatementList(int sourceLine, UnparsedStatement[] code, int off, int len)
    {
        this.code = new UnparsedStatement[len];
        this.sourceLine = sourceLine;
        System.arraycopy(check(code), off, this.code, 0, len);
    }
    private UnparsedStatementList(int sourceLine, UnparsedStatement[] code, boolean dummy) { this.code = code; this.sourceLine = sourceLine; }
    private UnparsedStatementList(int sourceLine, UnparsedStatement[] code, int off, int len, boolean dummy)
    {
        this.code = new UnparsedStatement[len];
        this.sourceLine = sourceLine;
        System.arraycopy(code, off, this.code, 0, len);
    }
    
    public final UnparsedStatementList copy() { return subList(0, code.length); }
    
    public final int length() { return code.length; }
    public final boolean isEmpty() { return code.length == 0; }
    
    public final void set(int index, UnparsedStatement code)
    {
        if(code == null)
            throw new NullPointerException();
        this.code[index] = code;
    }
    
    public final <US extends UnparsedStatement> US get(int index) { return (US) code[index]; }
    
    public final UnparsedStatementList subList(int offset, int length) { return new UnparsedStatementList(sourceLine, code, offset, length, false); }
    public final UnparsedStatementList subList(int offset) { return subList(offset, code.length - offset); }
    
    public final UnparsedStatementList concat(UnparsedStatementList clist) { return concat(clist.code); }
    public final UnparsedStatementList concat(Collection<? extends UnparsedStatement> c) { return concat(c.toArray(new UnparsedStatement[c.size()])); }
    public final UnparsedStatementList concat(UnparsedStatement... code)
    {
        UnparsedStatement[] array = new UnparsedStatement[this.code.length + code.length];
        System.arraycopy(this.code, 0, array, 0, this.code.length);
        System.arraycopy(check(code), 0, array, this.code.length, code.length);
        return new UnparsedStatementList(sourceLine, array, false);
    }
    public final UnparsedStatementList concat(UnparsedStatement code)
    {
        if(code == null)
            throw new NullPointerException();
        UnparsedStatement[] array = new UnparsedStatement[this.code.length + 1];
        System.arraycopy(this.code, 0, array, 0, this.code.length);
        array[array.length - 1] = code;
        return new UnparsedStatementList(sourceLine, array, false);
    }
    
    public final UnparsedStatementList concatFirst(UnparsedStatementList clist) { return concatFirst(clist.code); }
    public final UnparsedStatementList concatFirst(Collection<? extends UnparsedStatement> c) { return concatFirst(c.toArray(new UnparsedStatement[c.size()])); }
    public final UnparsedStatementList concatFirst(UnparsedStatement... code)
    {
        UnparsedStatement[] array = new UnparsedStatement[this.code.length + code.length];
        System.arraycopy(check(code), 0, array, 0, code.length);
        System.arraycopy(this.code, 0, array, code.length, this.code.length);
        return new UnparsedStatementList(sourceLine, array, false);
    }
    public final UnparsedStatementList concatFirst(UnparsedStatement code)
    {
        if(code == null)
            throw new NullPointerException();
        UnparsedStatement[] array = new UnparsedStatement[this.code.length + 1];
        System.arraycopy(this.code, 0, array, 1, this.code.length);
        array[0] = code;
        return new UnparsedStatementList(sourceLine, array, false);
    }
    
    public final UnparsedStatementList concatMiddle(int index, UnparsedStatementList clist) { return concatMiddle(index, clist.code); }
    public final UnparsedStatementList concatMiddle(int index, Collection<? extends UnparsedStatement> c) { return concatMiddle(index, c.toArray(new UnparsedStatement[c.size()])); }
    public final UnparsedStatementList concatMiddle(int index, UnparsedStatement... code)
    {
        if(index < 0 || index > this.code.length)
            throw new IllegalArgumentException("Index out of range: " + index);
        if(index == 0)
            return concatFirst(code);
        else if(index == this.code.length)
            return concat(code);
        UnparsedStatement[] array = new UnparsedStatement[this.code.length + code.length];
        System.arraycopy(this.code, 0, array, 0, index);
        System.arraycopy(check(code), 0, array, index, code.length);
        System.arraycopy(this.code, index, array, index + code.length, this.code.length - index);
        return new UnparsedStatementList(sourceLine, array, false);
    }
    public final UnparsedStatementList concatMiddle(int index, UnparsedStatement code)
    {
        if(code == null)
            throw new NullPointerException();
        if(index < 0 || index > this.code.length)
            throw new IllegalArgumentException("Index out of range: " + index);
        if(index == 0)
            return concatFirst(code);
        else if(index == this.code.length)
            return concat(code);
        UnparsedStatement[] array = new UnparsedStatement[this.code.length + 1];
        System.arraycopy(this.code, 0, array, 0, index);
        System.arraycopy(this.code, index, array, index + 1, this.code.length - index);
        array[index] = code;
        return new UnparsedStatementList(sourceLine, array, false);
    }
    
    public final UnparsedStatementList wrapBetween(UnparsedStatementList before, UnparsedStatementList after) { return wrapBetween(before.code, after.code); }
    public final UnparsedStatementList wrapBetween(UnparsedStatement before, UnparsedStatement after)
    {
        if(before == null)
            throw new NullPointerException();
        if(after == null)
            throw new NullPointerException();
        UnparsedStatement[] array = new UnparsedStatement[code.length + 2];
        System.arraycopy(code, 0, array, 1, code.length);
        array[0] = before;
        array[array.length - 1] = after;
        return new UnparsedStatementList(sourceLine, array, false);
    }
    public final UnparsedStatementList wrapBetween(UnparsedStatement[] before, UnparsedStatement[] after)
    {
        UnparsedStatement[] array = new UnparsedStatement[before.length + code.length + after.length];
        System.arraycopy(check(before), 0, array, 0, before.length);
        System.arraycopy(code, 0, array, before.length, code.length);
        System.arraycopy(check(after), 0, array, before.length + array.length, after.length);
        return new UnparsedStatementList(sourceLine, array, false);
    }
    
    public final UnparsedStatementList extract(UnparsedStatement from, UnparsedStatement to)
    {
        boolean init = false;
        int offset = -1, len = -1, idx = -1;
        for(UnparsedStatement c : code)
        {
            idx++;
            if(!init)
            {
                if(!c.equals(from))
                    continue;
                init = true;
                offset = idx;
                continue;
            }
            if(c.equals(to))
                break;
            len++;
        }
        return subList(offset, len);
    }
    
    public final int count(UnparsedStatement codePart)
    {
        int count = 0;
        for(UnparsedStatement c : code)
            if(c.equals(codePart))
                count++;
        return count;
    }
    
    public final boolean has(UnparsedStatement codePart)
    {
        for(UnparsedStatement cp : code)
            if(cp.equals(codePart))
                return true;
        return false;
    }
    
    public final int count(StatementType type)
    {
        int count = 0;
        for(UnparsedStatement c : code)
            if(c.getStatementType() == type)
                count++;
        return count;
    }
    
    public final int indexOf(UnparsedStatement code)
    {
        for(int i=0;i<this.code.length;i++)
            if(this.code[i].equals(code))
                return i;
        return -1;
    }
    
    public final int indexOf(StatementType type)
    {
        for(int i=0;i<code.length;i++)
            if(code[i].getStatementType() == type)
                return i;
        return -1;
    }
    
    public final UnparsedStatementList[] split(UnparsedStatement separator) { return split(separator, -1); }
    public final UnparsedStatementList[] split(UnparsedStatement separator, int limit)
    {
        if(code.length == 0)
            return new UnparsedStatementList[] { this };
        if(limit == 1)
            return new UnparsedStatementList[] { copy() };
        LinkedList<UnparsedStatementList> parts = new LinkedList<>();
        limit = limit < 1 ? -1 : limit;
        int i, off;
        for(i=0, off=0;i<code.length;i++)
            if(code[i].equals(separator) && limit != 0)
            {
                parts.add(subList(off, i - off));
                off = i + 1;
                limit--;
            }
        if(i > off)
            parts.add(subList(off, i - off));
        return parts.toArray(new UnparsedStatementList[parts.size()]);
    }
    
    final Pointer counter() { return new Pointer(); }
    final Pointer counter(int initialValue) { return new Pointer(initialValue); }
    
    @Override
    public final String toString() { return Arrays.toString(code); }
    
    private static UnparsedStatement[] check(UnparsedStatement[] code)
    {
        for(UnparsedStatement c : code)
            if(c == null)
                throw new NullPointerException();
        return code;
    }
    
    private static final UnparsedStatement[] EMPTY_ARRAY = {};
    public static final UnparsedStatementList empty(int sourceLine) { return new UnparsedStatementList(sourceLine, EMPTY_ARRAY); }
    
    public static final <IT, OT> OT[] mapArray(IT[] input, Mapper<IT, OT> mapper, OT[] output) throws CompilationError
    {
        int end = input.length > output.length ? output.length : input.length;
        for(int i=0;i<end;i++)
            output[i] = mapper.map(input[i]);
        return output;
    }
    
    public static final <IT, OT> OT[] mapArray(int offset, IT[] input, Mapper<IT, OT> mapper, OT[] output) throws CompilationError
    {
        int end = input.length > output.length ? output.length : input.length;
        for(int i=offset;i<end;i++)
            output[i] = mapper.map(input[i]);
        return output;
    }
    
    @FunctionalInterface
    public interface Mapper<I, O> { O map(I input) throws CompilationError; }
    
    
    final class Pointer
    {
        private int value;
        private final int limit;
        
        private Pointer(int initialValue)
        {
            this.value = initialValue;
            this.limit = code.length;
        }
        private Pointer() { this(0); }
        
        public final UnparsedStatementList list() { return UnparsedStatementList.this; }
        public final int increase(int times) { return value += times; }
        public final int increase() { return increase(1); }
        public final int decrease(int times) { return value -= times; }
        public final int decrease() { return decrease(1); }
        public final int value() { return value; }
        public final int finish() { return value = limit; }
        public final boolean end() { return value >= limit; }
        
        public final <US extends UnparsedStatement> US listValue() { return (US) code[value]; }
    }
}
