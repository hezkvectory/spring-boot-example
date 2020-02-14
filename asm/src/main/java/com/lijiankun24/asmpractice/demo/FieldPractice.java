package com.lijiankun24.asmpractice.demo;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.FileInputStream;
import java.io.InputStream;

public class FieldPractice {

    public static void main(String[] args) {
        addAgeField();
    }

    private static void addAgeField() {
        try {
            InputStream inputStream = new FileInputStream("/Users/hezhengkui/Documents/coohua/ideaworkspace/spring-boot-example/asm/target/classes/com/lijiankun24/asmpractice/demo/Person.class");
            ClassReader reader = new ClassReader(inputStream);
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);

            ClassVisitor visitor = new Transform(writer);
            reader.accept(visitor, ClassReader.SKIP_DEBUG);

            byte[] classFile = writer.toByteArray();
            MyClassLoader classLoader = new MyClassLoader();
            Class clazz = classLoader.defineClassFromClassFile("com.lijiankun24.asmpractice.demo.Person", classFile);
            Object obj = clazz.newInstance();

            System.out.println(clazz.getDeclaredField("name").get(obj)); //----(1)
            System.out.println(clazz.getDeclaredField("sex").get(obj));  //----(2)
            System.out.println(clazz.getDeclaredField("age").get(obj));  //----(2)
            System.out.println(clazz.getDeclaredField("age1").get(obj));  //----(2)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}