package alone.walker.optimization.thread.thread.transfer;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: huangYong
 * @Date: 2019/12/24 10:45
 */
public class  UserAccount {
    private String name;
    private Integer money;
    private final Lock lock = new ReentrantLock();

    public UserAccount(String name, Integer money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public Integer getMoney() {
        return money;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Lock getLock() {
        return lock;
    }

    public void addMoney(int amount){
        money = money + amount;
    }
    public void flyMoney(int amount){
        money = money - amount;
    }
}
