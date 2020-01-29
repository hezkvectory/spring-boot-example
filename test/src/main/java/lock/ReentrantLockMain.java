package lock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockMain {
    ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantLockMain main = new ReentrantLockMain();
        main.get();
    }

    public void get() {
        lock.lock();
        System.out.println(Thread.currentThread().getId());
        set();
        lock.unlock();
    }

    public void set() {
        lock.lock();
        System.out.println(Thread.currentThread().getId());
        lock.unlock();
    }
}
