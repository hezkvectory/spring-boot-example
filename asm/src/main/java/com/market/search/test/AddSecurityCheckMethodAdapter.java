package com.market.search.test;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by xiaotian on 2018/5/8.
 */
public class AddSecurityCheckMethodAdapter extends MethodVisitor {
    public AddSecurityCheckMethodAdapter(MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
    }

    public void visitCode() {
        visitMethodInsn(Opcodes.INVOKESTATIC, "com/market/search/test/SecurityChecker",
                "checkSecurity", "()Z", false);
        super.visitCode();
    }

}