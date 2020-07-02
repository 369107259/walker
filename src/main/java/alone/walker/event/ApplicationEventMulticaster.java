package alone.walker.event;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * @Author: huangYong
 * @Date: 2020/7/1 18:34
 */
//@Component
public class ApplicationEventMulticaster extends SimpleApplicationEventMulticaster {
    ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("event").build();

    private ExecutorService executorService;

    public ExecutorService getExecutorService() {
        return executorService;
    }
    public void setExecutorService(ExecutorService executorService) {
        this.executorService = new ThreadPoolExecutor(2, 4, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(64), threadFactory);;
    }

}
