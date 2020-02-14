package com.test.asm;

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.util.List;

public class Test {
    public static void main1(String[] args) throws Exception {
        ClassReader cr = new ClassReader(Bazhang.class.getName());
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);

        cr.accept(cw, Opcodes.ASM5);

        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "newFunc", "(Ljava/lang/String;)V", null, null);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitEnd();

        // 获取生成的class文件对应的二进制流
        byte[] code = cw.toByteArray();

        //将二进制流写到out/下
        FileOutputStream fos = new FileOutputStream("out/Bazhang222.class");
        fos.write(code);
        fos.close();

    }

    public static void main(String[] args) throws Exception {
        ClassPrinter printer = new ClassPrinter();
        //读取静态内部类Bazhang
        ClassReader cr = new ClassReader("com.test.asm.Test$Bazhang");
        cr.accept(printer, 0);

    }

    //静态内部类
    static class Bazhang {

        public Bazhang(int a) {
        }

        private int test(String line) {
            return 1;
        }

        private long f(int n, String s, int[] arr) {
            return 0;
        }

        private void hi(double a, List<String> b) {

        }
    }

    static class ClassPrinter extends ClassVisitor {

        public ClassPrinter() {
            super(Opcodes.ASM5);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces);
            //打印出父类name和本类name
            System.out.println(superName + " " + name);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            //打印出方法名和类型签名
            System.out.println(name + " " + desc);
            return super.visitMethod(access, name, desc, signature, exceptions);
        }
    }

}