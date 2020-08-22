package alone.walker.optimization.thread;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.weaver.ast.ITestVisitor;
import org.aspectj.weaver.ast.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Author: huangYong
 * @Date: 2020/8/22 17:20
 */
public class FutureTaskEx {
    private ExecutorService executorService = Executors.newFixedThreadPool(10);


    class  NeiBu implements Callable<List<Object>>{
        private  Long item;
        public NeiBu(Long itemId) {
            this.item = itemId;
        }

        @Override
        public List<Object> call() throws Exception {
            //todo 根据item处理你异步处理的业务逻辑
            return new ArrayList<>();
        }
    }

    public List<Object>  doThings(){
        List<Object> result = new CopyOnWriteArrayList<>();

        List<Object> list = new ArrayList<>();//你的list
        list.parallelStream().forEach(o -> {
            //item传进去
            NeiBu neiBu = new NeiBu(12L);
            //FutureTask 为Runnable的子类
            FutureTask<List<Object>> futureTask = new FutureTask<List<Object>>(neiBu);
            executorService.submit(futureTask);
            try {
                List<Object> objects = futureTask.get();
                result.addAll(objects);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
      return result;
    }



}
