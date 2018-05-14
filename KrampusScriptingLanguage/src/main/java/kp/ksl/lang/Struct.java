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
public abstract class Struct
{
    public abstract int getFieldCount();
    
    public abstract Object getFieldValue(String fieldName);
    
    public abstract void setFieldValue(String fieldName, Object value);
}
