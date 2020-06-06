package alone.walker.optimization.thread.thread.transfer;

import java.util.Random;

/**
 * @Author: huangYong
 * @Date: 2019/12/24 11:00
 * 拿锁
 */
public class TransferTry implements ITransfer {
    /**
     * 转账
     * @param from
     * @param to
     * @param amount
     */
    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) throws InterruptedException {
        String theadName = Thread.currentThread().getName();
        Random random = new Random();
        while (true){
            if (from.getLock().tryLock()){
                System.out.printf(theadName+" get "+from);
                try {
                    if (to.getLock().tryLock()){
                        try {
                            System.out.printf(theadName+" get "+to);
                            from.flyMoney(amount);
                            to.addMoney(amount);
                            break;
                        } finally {
                            to.getLock().unlock();
                        }
                    }
                }finally {
                    from.getLock().unlock();
                }
            }
            //避免活锁（不停拿锁，不停放锁）
            Thread.sleep(random.nextInt(10));
        }
    }
}
