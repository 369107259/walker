package alone.walker.optimization.thread.thread.transfer;

/**
 * @Author: huangYong
 * @Date: 2019/12/24 10:46
 */
public interface ITransfer {
    /**
     * 转账
     * @param from
     * @param to
     * @param amount
     */
    void transfer(UserAccount from, UserAccount to, int amount) throws InterruptedException;
}
