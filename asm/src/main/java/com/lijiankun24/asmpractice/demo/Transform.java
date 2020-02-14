package com.lijiankun24.asmpractice.demo;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class Transform extends ClassVisitor {

    public Transform(ClassVisitor classVisitor) {
        super(Opcodes.ASM6, classVisitor);
    }

    @Override
    public void visitEnd() {
        cv.visitField(Opcodes.ACC_PUBLIC, "age", Type.getDescriptor(int.class), null, 12);
        cv.visitField(Opcodes.ACC_STATIC | Opcodes.ACC_PUBLIC, "age1", Type.getDescriptor(int.class), null, 122);
    }
} 