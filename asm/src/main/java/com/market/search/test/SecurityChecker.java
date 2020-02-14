package com.market.search.test;

/**
 * Created by xiaotian on 2018/5/8.
 * 将checkSecurity 通过ASE方式动态 增强Account.operation 中
 */
public class SecurityChecker {

    public static boolean checkSecurity() {
        System.out.println("SecurityChecker.checkSecurity ...");
        return true;
    }

}