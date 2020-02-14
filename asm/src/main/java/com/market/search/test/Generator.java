package com.market.search.test;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by xiaotian on 2018/5/8.
 */
public class Generator {
    private static AccountGeneratorClassLoader classLoader = new AccountGeneratorClassLoader();

    public static void main(String args[]) throws Exception {
        ClassReader cr = new ClassReader("com.market.search.test.Account");
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        AddSecurityCheckClassAdapter classAdapter = new AddSecurityCheckClassAdapter(cw);
        cr.accept(classAdapter, ClassReader.SKIP_DEBUG);

        byte[] data = cw.toByteArray();
        //File file = new File("C:\\Users\\xiaotian\\Desktop\\jvm原理\\Account.class");
        File file = new File("/Users/hezhengkui/Documents/coohua/ideaworkspace/spring-boot-example/asm/target/classes/com/market/search/test/Account.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();

//        Class account = classLoader.defineClassFromClassFile("com.market.search.test.Account", data);
//        Account acc =(Account) account.newInstance();
//        acc.operation();
        Account account = new Account();
        account.operation();


    }

    private static class AccountGeneratorClassLoader extends ClassLoader {
        public Class defineClassFromClassFile(String className, byte[] classFile) throws ClassFormatError {
            return defineClass(className, classFile, 0, classFile.length);
        }
    }
}