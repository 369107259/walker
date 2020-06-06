package alone.walker.optimization.thread.thread.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author: huangYong
 * @Date: 2020/3/19 16:50
 * 手写jdk Lock锁
 * 1:保证线程操作的原子性(AtomicReference)
 * 2：线程的阻塞和唤醒 (LockSupport)
 * 3：等待列表 (LinkedBlockingQueue)
 */
public class JdkLock implements Lock {

    /***
     * 原子操作
     */
    private  AtomicReference<Thread> atomicReference = new AtomicReference<>();

    /***
     * 等待列表
     */
    public LinkedBlockingQueue<Thread> queue = new LinkedBlockingQueue<>();


    /***
     * 加锁
     */
    @Override
    public void lock() {
        //期望原子操作类中线程为空，将其更新成当前执行线程。否--存在线程已经在执行中
        while (!atomicReference.compareAndSet(null,Thread.currentThread())){
            //将当前线程加入等待列表中
            queue.add(Thread.currentThread());
            //阻塞当前线程
            LockSupport.park();
            //唤醒后 移除等待列表
            queue.remove(Thread.currentThread());
        }
    }

    /***
     * 解锁
     */
    @Override
    public void unlock() {
        if (atomicReference.compareAndSet(Thread.currentThread(),null)){
            //如果线程结束，调用解锁则将等待列表中所有线程唤醒
            Object[] objects = queue.toArray();
            for (Object o:objects){
                Thread thread = (Thread) o;
                LockSupport.unpark(thread);
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
