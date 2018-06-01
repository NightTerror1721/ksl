/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import kp.ksl.compiler.meta.Variable;
import kp.ksl.lang.KSLClassLoader;
import kp.ksl.lang.Struct;
import org.apache.bcel.generic.ObjectType;

/**
 *
 * @author Asus
 */
public final class KSLStruct extends KSLType
{
    private final HashMap<String, StructField> fields = new HashMap<>();
    
    private KSLStruct(Class<?> jclass)
    {
        super(Typeid.structTypeid(jclass), Typename.structName(jclass), new ObjectType(jclass.getName()), jclass);
    }
    
    @Override
    public final boolean isMutable() { return true; }

    @Override
    public final boolean isStruct() { return true; }

    @Override
    public final boolean isValidField(String field) { return fields.containsKey(field); }
    
    @Override
    public final Variable getField(String field) { return fields.get(field); }
    
    
    public static final KSLStruct createStruct(Class<?> jclass, KSLClassLoader loader)
    {
        if(Struct.class.isAssignableFrom(jclass))
            throw new IllegalArgumentException(jclass + " is not a valid struct class");
        
        KSLStruct s = new KSLStruct(jclass);
        s.loadFields(loader);
        return s;
    }
    
    private void loadFields(KSLClassLoader loader)
    {
        for(Field field : jclass.getFields())
        {
            int mod = field.getModifiers();
            if(Modifier.isFinal(mod) || Modifier.isStatic(mod) || !Modifier.isPublic(mod))
                continue;
            StructField sfield = new StructField(loader, field);
            fields.put(sfield.getName(), sfield);
        }
    }

    @Override
    public final boolean isManualAssignableFrom(KSLType type)
    {
        return is(type) || (type.isStruct() &&
                (jclass.isAssignableFrom(type.getJavaClass()) || type.getJavaClass().isAssignableFrom(jclass)));
    }

    @Override
    public final boolean isAutoAssignableFrom(KSLType type)
    {
        return is(type) || (type.isStruct() && jclass.isAssignableFrom(type.getJavaClass()));
    }
    
    
    
    private final class StructField extends Variable
    {
        private final KSLClassLoader classLoader;
        private final Field field;
        private KSLType type;
        
        private StructField(KSLClassLoader classLoader, Field field)
        {
            super();
            this.classLoader = classLoader;
            this.field = field;
        }
        
        @Override
        public final KSLType getTypeOwner() { return KSLStruct.this; }
        
        @Override
        public final String getName() { return field.getName(); }

        @Override
        public final KSLType getType()
        {
            if(type != null)
                return type;
            try { return type = classLoader.findKSLType(field.getType()); }
            catch(ClassNotFoundException ex) { throw new RuntimeException(ex); }
        }

        @Override
        public final int getLocalReference() { return -1; }

        @Override
        public final boolean isLocal() { return false; }

        @Override
        public final boolean isField() { return true; }

        @Override
        public final boolean isConst() { return false; }

        @Override
        public final boolean isInitiated() { return true; }
    }
}
