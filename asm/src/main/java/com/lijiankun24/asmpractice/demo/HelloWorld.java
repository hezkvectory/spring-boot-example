package com.lijiankun24.asmpractice.demo;

public class HelloWorld {

    public void sayHello() {
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}