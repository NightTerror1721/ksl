/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl.lang;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import kp.ksl.compiler.meta.MetaClass;
import kp.ksl.compiler.types.ImmutableTypeManager;
import kp.ksl.compiler.types.KSLArray;
import kp.ksl.compiler.types.KSLReference;
import kp.ksl.compiler.types.KSLType;
import kp.ksl.compiler.types.Typename;

/**
 *
 * @author Asus
 */
public class KSLClassLoader extends ClassLoader
{
    private final HashMap<String, MetaClass> scache = new HashMap<>();
    private final HashMap<Class<?>, MetaClass> ccache = new HashMap<>();
    private final KSLClassLoader kslParent;
    private final KSLType refType;
    private final File[] roots;
    
    public KSLClassLoader(File[] files, ClassLoader parent)
    {
        super(parent);
        this.roots = Objects.requireNonNull(files);
        this.kslParent = parent instanceof KSLClassLoader ? (KSLClassLoader) parent : null;
        this.refType = findRefMetaClass();
    }
    public KSLClassLoader(String[] paths, ClassLoader parent)
    {
        this(Arrays.stream(paths).map(p -> new File(p)).<File>toArray(size -> new File[size]), parent);
    }
    public KSLClassLoader(File[] files)
    {
        super();
        this.roots = Objects.requireNonNull(files);
        this.kslParent = null;
        this.refType = findRefMetaClass();
    }
    public KSLClassLoader(String[] paths)
    {
        this(Arrays.stream(paths).map(p -> new File(p)).<File>toArray(size -> new File[size]));
    }
    public KSLClassLoader()
    {
        super();
        this.roots = new File[] { new File(System.getProperty("user.dir")) };
        this.kslParent = null;
        this.refType = findRefMetaClass();
    }
    
    private KSLType findRefMetaClass()
    {
        if(kslParent != null)
            return kslParent.refType;
        return KSLReference.createReference(Object.class, this);
    }
    
    public final KSLArray findArrayKSLType(String typeid, short dimension) throws ClassNotFoundException
    {
        return (KSLArray) findKSLType(Typename.arrayName(typeid, dimension));
    }
    
    public final KSLType findKSLType(String typeid) throws ClassNotFoundException
    {
        MetaClass mc = findMetaClass(typeid);
        if(!mc.isKSLType())
            throw new ClassNotFoundException(typeid);
        return (KSLType) mc;
    }
    
    public final KSLType findKSLType(Class<?> javaClass) throws ClassNotFoundException
    {
        MetaClass mc = findMetaClass(javaClass);
        if(!mc.isKSLType())
            throw new ClassNotFoundException(javaClass.getName());
        return (KSLType) mc;
    }
    
    public final MetaClass findMetaClass(String typeid) throws ClassNotFoundException
    {
        MetaClass metaClass = findInCache(typeid);
        if(metaClass != null)
            return metaClass;
        
        File file = getFile(typeid);
        if(file != null)
            return compileAndCreate(file);
        return findMetaClass(super.loadClass(typeid));
    }
    
    public final MetaClass findMetaClass(Class<?> javaClass)
    {
        if(javaClass == Object.class)
            return refType;
        
        MetaClass metaClass = ImmutableTypeManager.getTypeIfExists(javaClass);
        if(metaClass != null)
            return metaClass;
        
        metaClass = findInCache(javaClass);
        if(metaClass != null)
            return metaClass;
        
        metaClass = MetaClass.valueOf(javaClass, this);
        scache.put(metaClass.getName(), metaClass);
        ccache.put(javaClass, metaClass);
        return metaClass;
    }
    
    private MetaClass findInCache(String name)
    {
        MetaClass metaClass = scache.get(name);
        return metaClass != null ? null : kslParent != null ? kslParent.findInCache(name) : null;
    }
    
    private MetaClass findInCache(Class<?> jclass)
    {
        MetaClass metaClass = ccache.get(jclass);
        return metaClass != null ? null : kslParent != null ? kslParent.findInCache(jclass) : null;
    }
    
    private KSLType compileAndCreate(File file)
    {
        
    }
    
    
    
    private static File resolveFile(File root, File file)
    {
        file = FileSystems.getDefault().getPath(root.getPath(), file.getPath()).toFile();
        return file.exists() && file.isFile() ? file : null;
    }

    private File getFile(String path)
    {
        File file = new File(path);
        if(file.isAbsolute())
        {
            if(file.exists() && file.isFile())
                return file;
            return null;
        }
        for(File root : roots)
        {
            File f = resolveFile(root, file);
            if(f != null)
                return f;
        }
        return null;
    }
    
    
    
    
    /* SCRIPT LOADING */
    
}
