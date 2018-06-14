/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl;

import java.util.function.Function;

/**
 *
 * @author Asus
 */
public final class Main
{
    public static void main(String[] args) throws ClassNotFoundException
    {
        /*System.out.println(Integer.TYPE);
        System.out.println(Class.forName("[" + Class[].class.getName()));
        System.out.println(Number[][].class.isAssignableFrom(Integer[][].class));
        System.out.println(Integer.TYPE.isAssignableFrom(Short.TYPE));
        Path p = FileSystems.getDefault().getPath(new File(System.getProperty("user.dir")).getPath(), new File("src/kp").getPath());*/
        
        //java.lang.invoke.LambdaMetafactory.
        
        Runnable r = () -> System.out.println(args);
        Function<Object, String> s = String::valueOf;
        
        r.run();
        s.apply(Main.class);
    }
}
