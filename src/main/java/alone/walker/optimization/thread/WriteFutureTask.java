package alone.walker.optimization.thread;

import java.util.concurrent.*;

/**
 * @Author: huangYong
 * @Date: 2020/3/10 14:17
 * 手写FutureTask类,实现Thread线程调用callable线程
 * 与jdk阻塞方式不同
 */
public class WriteFutureTask<V> implements Runnable, Future<V> {
    /***
     * 将Callable线程作为成员变量
     */
    private Callable<V> callable;
    /***
     * 线程调用返回的结果
     */
    private V result = null;

    public WriteFutureTask(Callable<V> callable) {
        this.callable = callable;
    }

    @Override
    public void run() {
        try {
            result = callable.call();
            synchronized (this){
                this.notifyAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        if (result != null){
            return result;
        }
        //阻塞
        synchronized (this){
            this.wait();
        }
        return result;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
