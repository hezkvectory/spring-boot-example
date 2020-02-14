package com.lijiankun24.asmpractice.demo;

public class MyClassLoader extends ClassLoader {



    public Class defineClassFromClassFile(String className, byte[] classFile)

            throws ClassFormatError {

        return defineClass(className, classFile, 0, classFile.length);

    }

}
