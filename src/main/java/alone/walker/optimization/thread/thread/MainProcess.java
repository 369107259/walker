package alone.walker.optimization.thread.thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @Author: huangYong
 * @Date: 2019/12/20 9:50
 */
public class MainProcess {
    public static void main(String[] args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo t :
                threadInfos) {
            System.out.println("=====" + t.getThreadName());
        }
    }
}
