package com.test.util;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapMain {
    static final int HASH_BITS = 0x7fffffff; // usable bits of normal node hash

    private static final int MAXIMUM_CAPACITY = 1 << 30;
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;
    static Map<String, String> map = new ConcurrentHashMap<>();
    static Field sizeCtl;
    static Field table;

    static {
        sizeCtl = ReflectionUtils.findField(ConcurrentHashMap.class, "sizeCtl");
        sizeCtl.setAccessible(true);
        table = ReflectionUtils.findField(ConcurrentHashMap.class, "table");
        table.setAccessible(true);
    }

    public static void main(String[] args) throws IllegalAccessException {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
//        System.out.println(queue.remove());
        System.out.println(queue.poll());
//        System.out.println(-1 << 29);
//        System.out.println(0 << 29);
//        System.out.println(1 << 29);
//        System.out.println(2 << 29);
//        System.out.println(3 << 29);
//        System.out.println(CAPACITY);
//        System.out.println(~CAPACITY);
//        System.out.println(Math.round(-1.5));
//        System.out.println(Math.round(1.5));
//        Hashtable hashtable = new Hashtable();
//        hashtable.put(null, 1);
//        int n = 16;
//        int sc = n - (n >>> 2);
//        System.out.println(n >>> 2);
//        System.out.println(sc);
//        System.out.println(HASH_BITS);
//        int h = 100;
//        System.out.println(h >>> 16);
//        System.out.println((h ^ (h >>> 16)) & HASH_BITS);

//        System.out.println(MAXIMUM_CAPACITY);
//        System.out.println(tableSizeFor(18));

//        int i = 0;
//        for (; i < 10; i++) {
//            map.put("k" + i, "v" + i);
//        }
//
//        System.out.println("i----" + sizeCtl.get(map));
//        ConcurrentHashMap.Entry[] m1 = (ConcurrentHashMap.Entry[]) table.get(map);
//        System.out.println(m1);
//        for (; i < 15; i++) {
//            map.put("k" + i, "v" + i);
//        }
//
//        System.out.println("j----" + sizeCtl.get(map));
//        m1 = (ConcurrentHashMap.Entry[]) table.get(map);
//        System.out.println(m1);
//        for (; i < 20; i++) {
//            map.put("k" + i, "v" + i);
//        }
//        System.out.println("v----" + sizeCtl.get(map));
//        m1 = (ConcurrentHashMap.Entry[]) table.get(map);
//        System.out.println(m1);

    }

    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
