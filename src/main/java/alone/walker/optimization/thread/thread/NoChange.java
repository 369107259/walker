package alone.walker.optimization.thread.thread;

/**
 * @Author: huangYong
 * @Date: 2019/12/23 15:45
 * 不可变类 不存在线程安全问题
 */
public class NoChange {
    private final int i;
    private final int j;

    public NoChange(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
