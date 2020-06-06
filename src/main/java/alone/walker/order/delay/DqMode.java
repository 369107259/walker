package alone.walker.order.delay;


import alone.walker.order.dao.Order;
import alone.walker.order.service.DlyOrderProcessor;
import alone.walker.order.vo.ItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.DelayQueue;

/**
 * @Author: huangYong
 * @Date: 2020/5/3 13:17
 */
@Component
@Slf4j
public class DqMode {
    private static DelayQueue<ItemVo<Order>> delayQueue = new DelayQueue<>();

    @Autowired
    private DlyOrderProcessor dlyOrderProcessor;

    public void orderDelay(Order order, Long expireTime) {
        /*将订单放入延迟队列*/
        ItemVo<Order> itemVo = new ItemVo<>(expireTime,order);
        delayQueue.put(itemVo);
        log.info("订单超时时长{}秒,被推入本地队列,订单详情：{}", expireTime, order);
    }

    /***
     * 处理订单到期的业务
     */
    private static class TakeOrder implements Runnable {

        private DlyOrderProcessor dlyOrderProcessor;

        public TakeOrder(DlyOrderProcessor dlyOrderProcessor) {
            this.dlyOrderProcessor = dlyOrderProcessor;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                    /*获取队列中的订单，当take方法有返回时，说明订单已经过期*/
                try {
                    ItemVo<Order> take = delayQueue.take();
                    if (!ObjectUtils.isEmpty(take)){
                        dlyOrderProcessor.checkDelayOrder(take.getData());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Thread takeOrder;

    /***
     * 服务启动时，会执行有PostConstruct注解的方法
     */
    @PostConstruct
    private void init(){
        takeOrder = new Thread(new TakeOrder(dlyOrderProcessor));
        takeOrder.start();
    }

    @PreDestroy
    public void close(){
        takeOrder.interrupt();
    }
}
