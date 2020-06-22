package alone.walker.order;

import alone.walker.WalkerApplication;
import alone.walker.optimization.concurrent.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: huangYong
 * @Date: 2020/4/9 16:42
 */
@SpringBootTest(classes = WalkerApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
class OrderControllerTest {

    private final int number = 1000;
    @Autowired
    private OrderService orderService;

    //定义并发请求发射器
    private CountDownLatch countDownLatch= new CountDownLatch(number);

    /***
     * 并发测试 ：模拟1000个用户同时请求
     */
    @Test
    void queryOrderInfo() {
        for (int i = 0; i < number; i++) {

            Thread thread = new Thread(() -> {
                try {
                    //阻塞子线程执行
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Map<String, Object> stringObjectMap = orderService.queryOrderInfo("111111");
                System.out.print(stringObjectMap.toString());
            });
            //发射器数量值减一
            countDownLatch.countDown();
        }
        try {
            //通过延迟主线程时间,等待子线程完成,实现子线程日志信息打印
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}