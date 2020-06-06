package alone.walker.optimization.concurrent.service;

import alone.walker.optimization.concurrent.remoteService.RemoteServiceCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Author: huangYong
 * @Date: 2020/4/9 16:31
 * 模拟测试并发请求
 * 针对并发请求得性能调优得改造方法
 */
@Service
public class PerformanceOrderService {
    /***
     * 内部类,用于封装请求到队列中，批量请求第三方接口
     */
    class Request{
        private String orderCode;
        CompletableFuture<Map<String,Object>> future;
    }

    /***
     * 第三方接口服务
     */
    @Autowired
    private RemoteServiceCall remoteServiceCall;

    /***
     * 申明得队列
     */
    public LinkedBlockingQueue<Request> queue=new LinkedBlockingQueue<>();

    /**
     * 并发请求得方法入口
     * @param orderCode
     * @return
     */
    public Map<String,Object> queryOrderInfo(String orderCode) throws ExecutionException, InterruptedException {
        Request request = new Request();
        request.orderCode = orderCode;
        //jdk1.8新特性  将请求结果一一转发到对应得线程
        CompletableFuture<Map<String,Object>> future = new CompletableFuture<>();
        //将当前得线程和orderCode相对应
        request.future = future;
        queue.add(request);

        //判断当前orderCode对应得线程是否有返回值，有得话返回对应得结果
        return future.get();
    }

    /***
     * 系统启动扫描@service类时，发现类得方法有@PostConstruct注解，会执行此方法。
     * 设置定时任务处理器：
     */
    @PostConstruct
    public void init(){
        //定时任务处理器池
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        //创建定时任务处理器
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (queue.isEmpty()){
                    return;
                }
                List<Request> requests = new ArrayList<>(queue);
                for (int i = 0; i < queue.size(); i++) {
                    Request poll = queue.poll();
                    requests.add(poll);
                }
                List<String> orderCodeList = requests.stream().map(t -> t.orderCode).collect(Collectors.toList());
                //批量查询三方接口
                List<Map<String, Object>> orderInfoBatch = remoteServiceCall.queryOrderInfoBatch(orderCodeList);
                //唯一标识orderCode作为主键转为map, 将唯一标识与查询第三方结果集一一对应
                Map<String,Map<String, Object>> orderInfoMap = new HashMap<>();
                for (Map<String, Object> result:orderInfoBatch
                     ) {
                    orderInfoMap.put(String.valueOf(result.get("orderCode")),result);
                }
                //通过future来进行匹配响应，进行下发对应得线程
                requests.forEach(t->{
                    Map<String, Object> res = orderInfoMap.get(t.orderCode);
                    CompletableFuture<Map<String, Object>> future = t.future;
                    future.complete(res);
                });
            }
        },0,10,TimeUnit.MICROSECONDS);

    }
}
