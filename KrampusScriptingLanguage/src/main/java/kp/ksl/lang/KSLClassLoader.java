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

/**
 *
 * @author Asus
 */
public class KSLClassLoader extends ClassLoader
{
    private final HashMap<String, MetaClass<?>> cache = new HashMap<>();
    private final KSLClassLoader kslParent;
    private final File[] roots;
    
    public KSLClassLoader(File[] files, ClassLoader parent)
    {
        super(parent);
        this.roots = Objects.requireNonNull(files);
        this.kslParent = parent instanceof KSLClassLoader ? (KSLClassLoader) parent : null;
    }
    public KSLClassLoader(String[] paths, ClassLoader parent)
    {
        this(Arrays.stream(paths).map(p -> new File(p)).<File>toArray(size -> new File[size]), parent);
    }
    public KSLClassLoader(File[] files)
    {
        super();
        this.roots = new File[] { new File(System.getProperty("user.dir")) };
        this.kslParent = null;
    }
    public KSLClassLoader(String[] paths)
    {
        this(Arrays.stream(paths).map(p -> new File(p)).<File>toArray(size -> new File[size]));
    }
    
    public final MetaClass<?> findMetaClass(String name) throws ClassNotFoundException
    {
        MetaClass<?> metaClass = cache.get(name);
        if(metaClass != null)
            return metaClass;
        
        File file = getFile(name);
        if(file != null)
            return compileAndCreate(file);
        
        Class<?> jclass = super.loadClass(name);
        return MetaClass.valueOf(jclass);
    }
    
    private MetaClass<?> compileAndCreate(File file)
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
}
