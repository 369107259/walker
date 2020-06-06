package alone.walker.optimization.thread.thread;

/**
 * @Author: huangYong
 * @Date: 2019/12/23 15:41
 * 无状态类，不存在线程安全问题
 */
public class StateLess {
    public void add(int i){
        //.......
    }

    //CAS 保证线程安全  ----乐观锁   jdk提供的Atomic开头的类的实现方式

    //加锁  ----悲观锁   synchronized关键字
}
