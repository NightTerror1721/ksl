/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kp.ksl;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 *
 * @author Asus
 */
public final class Main
{
    public static void main(String[] args)
    {
        System.out.println(Integer.TYPE);
        Path p = FileSystems.getDefault().getPath(new File(System.getProperty("user.dir")).getPath(), new File("src/kp").getPath());
    }
}
