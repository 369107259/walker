package alone.walker.optimization.thread;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.*;

/**
 * @Author: huangYong
 * @Date: 2020/3/10 10:25
 * 性能优化-线程并行执行
 * 多线程同时调用一个服务使用cas原理来保持幂等性(设置缓存标识判断是否有其他线程调用)
 */
public class Parallel {
    /***
     * jdk自带线程池
     */
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    
    public void thread() throws ExecutionException, InterruptedException {
        //Runnable实现多线程(无返回值)
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();

        //Callable实现多线程
        Callable<JSONObject> callable = new Callable<JSONObject>() {
            @Override
            public JSONObject call() throws Exception {
                return new JSONObject();
            }
        };
        //FutureTask 为Runnable的子类
        FutureTask<JSONObject> futureTask = new FutureTask<JSONObject>(callable);
        //线程启动
        new Thread(futureTask).start();
        //优化使用线程池启动(jdk自带线程池)
        executorService.submit(futureTask);
        //获取新启线程返回值  方法阻塞
        JSONObject jsonObject = futureTask.get();
    }
}
