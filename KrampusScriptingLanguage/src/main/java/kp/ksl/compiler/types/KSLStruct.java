/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.compiler.types;

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
    
    @Override
    final String typeid() { return typeid; }

    @Override
    public final String getName() { return name; }

    @Override
    public boolean isPrimitive() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isStruct() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isReference() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public short getDimension() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public KSLType getBaseType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean canCastTo(KSLType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isValidField(String field) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public final KSLStruct.KSLStructField getField(String field) { throw new UnsupportedOperationException(); }
    
    @Override
    public final int getFieldCount() { throw new UnsupportedOperationException(); }
    
    @Override
    public final List<KSLStructField> getAllFields() { throw new UnsupportedOperationException(); }
    
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
