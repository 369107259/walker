package alone.walker.optimization.thread.thread.transfer;

/**
 * @Author: huangYong
 * @Date: 2019/12/24 10:46
 * 加锁
 */
public class TransferImpl implements ITransfer {
    private static Object tieLock = new Object();
    /**
     * 转账
     * @param from
     * @param to
     * @param amount
     */
    @Override
    public void transfer(UserAccount from, UserAccount to, int amount) {
        String theadName = Thread.currentThread().getName();
        int fromHashCode = System.identityHashCode(from);
        int toHashCode = System.identityHashCode(to);
        if (fromHashCode<toHashCode){
            synchronized (from){
                System.out.printf(theadName+" get "+from);
                synchronized (to){
                    System.out.printf(theadName+" get "+to);
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }
            }
        }else if (fromHashCode>toHashCode){
            synchronized (to){
                System.out.printf(theadName+" get "+to);
                synchronized (from){
                    System.out.printf(theadName+" get "+from);
                    from.flyMoney(amount);
                    to.addMoney(amount);
                }
            }
        }else {
            synchronized (tieLock){
                synchronized (from){
                    System.out.printf(theadName+" get "+from);
                    synchronized (to){
                        System.out.printf(theadName+" get "+to);
                        from.flyMoney(amount);
                        to.addMoney(amount);
                    }
                }
            }
        }
    }
}
