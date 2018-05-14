/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kp.ksl.compiler.parser.Identifier;
import org.apache.bcel.generic.Type;

/**
 *
 * @author Marc
 */
public final class KSLStruct extends KSLType
{
    private final HashMap<String, KSLStructField> fields;
    
    private KSLStruct(HashMap<String, KSLStructField> fields, Type type)
    {
        super(Typeid.structId(fields.values()), Typename.structName(fields.values()), type);
        this.fields = new HashMap<>(fields);
    }
    
    @Override
    public final boolean isMutable() { return true; }

    @Override
    public final boolean isPrimitive() { return false; }
    
    @Override
    public final boolean isString() { return false; }

    @Override
    public final boolean isArray() { return false; }

    @Override
    public final boolean isStruct() { return true; }

    @Override
    public final boolean isReference() { return false; }

    @Override
    public final short getDimension() { throw new UnsupportedOperationException(); }

    @Override
    public final KSLType getBaseType() { throw new UnsupportedOperationException(); }

    @Override
    public final boolean isValidField(String field) { return fields.containsKey(field); }
    
    @Override
    public final KSLStructField getField(String field) { return fields.get(field); }
    
    @Override
    public final int getFieldCount() { return fields.size(); }
    
    @Override
    public final List<KSLStructField> getAllFields() { return new ArrayList<>(fields.values()); }
    
    @Override
    public final boolean canCastTo(KSLType type) { return is(type); }
    
    public static final class KSLStructField
    {
        private final String name;
        private final KSLType type;
        
        private KSLStructField(String name, KSLType type)
        {
            this.name = name;
            this.type = type;
        }
        
        public final String getName() { return name; }
        public final KSLType getType() { return type; }
    }
    
    public static final class KSLStructBuilder
    {
        private final HashMap<String, KSLStructField> fields = new HashMap<>();
        
        private KSLStructBuilder() {}
        
        public final KSLStructBuilder addField(String name, KSLType type)
        {
            if(!Identifier.isValidIdentifier(name))
                throw new IllegalArgumentException();
            if(type == null)
                throw new NullPointerException();
            KSLStructField field = new KSLStructField(name, type);
            fields.put(name, field);
            return this;
        }
        
        public final KSLStruct build(Type type) { return new KSLStruct(fields, type); }
    }
}
