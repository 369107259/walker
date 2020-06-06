package alone.walker.optimization.thread;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * @Author: huangYong
 * @Date: 2020/3/10 17:30
 * 性能优化-tomcat
 */
@RestController
public class TomcatThread {
    @GetMapping
    public Callable<String> thread() {
        System.out.println("主线程" + Thread.currentThread().getName() + "开始=====" + System.currentTimeMillis());
        /***
         * 新建一个子线程执行业务逻辑，释放tomcat线程池中的线程
         */
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("子线程" + Thread.currentThread().getName() + "开始=====" + System.currentTimeMillis());
                //业务逻辑
                System.out.println("子线程" + Thread.currentThread().getName() + "结束=====" + System.currentTimeMillis());
                return new JSONObject().toString();
            }
        };
        System.out.println("主线程" + Thread.currentThread().getName() + "结束=====" + System.currentTimeMillis());
        return callable;
    }
}
