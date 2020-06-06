package alone.walker.optimization.thread.thread;

/**
 * @Author: huangYong
 * @Date: 2019/12/23 16:18
 * 演示死锁产生 静态的
 * 解决死锁的办法就是保持加锁顺序一致
 */
public class NormalDeadLock {

    private static Object first = new Object();

    private static Object second = new Object();

    private static void firstSecond() throws InterruptedException {
        String theadName = Thread.currentThread().getName();
        synchronized (first){
            System.out.println(theadName+"get first");
            Thread.sleep(100);
            synchronized (second){
                System.out.println(theadName+"get second");
            }
        }

    }

    private static void secondFirst() throws InterruptedException {
        String theadName = Thread.currentThread().getName();
        //first
        synchronized (second){
            System.out.println(theadName+"get second");
            Thread.sleep(100);
            //second
            synchronized (first){
                System.out.println(theadName+"get first");
            }
        }
    }

    public static class SubThead extends Thread {
        private String name;

        public SubThead(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            Thread.currentThread().setName(name);
            try {
                secondFirst();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().setName("MainThead");
        SubThead subThead = new SubThead("SubThead");
        subThead.start();
        firstSecond();
    }
}
