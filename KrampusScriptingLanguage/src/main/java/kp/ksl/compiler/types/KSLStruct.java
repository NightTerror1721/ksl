/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Marc
 */
public final class KSLStruct extends KSLType
{
    private final String typeid;
    private final String name;
    private final HashMap<String, KSLStructField> fields;
    
    private KSLStruct(KSLStructField[] fields)
    {
        this.typeid = Typeid.generateStructTypeid(fields);
        this.name = generateName(fields);
        this.fields = new HashMap<>();
        for(KSLStructField field : fields)
            this.fields.put(field.name, field);
    }
    
    private static String generateName(KSLStructField[] fields)
    {
        StringBuilder sb = new StringBuilder("struct {");
        if(fields.length > 0)
        {
            for(KSLStructField field : fields)
                sb.append(field.getType()).append(" ").append(field.name).append("; ");
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.append("}").toString();
    }
    
    @Override
    final String typeid() { return typeid; }

    @Override
    public final String getName() { return name; }

    @Override
    public final boolean isPrimitive() { return false; }

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
}
