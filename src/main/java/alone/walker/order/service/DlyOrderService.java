package alone.walker.order.service;


import alone.walker.order.dao.Order;
import alone.walker.order.dao.OrderDao;
import alone.walker.order.delay.DqMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @Author: huangYong
 * @Date: 2020/5/3 12:57
 * 订单操作服务类
 */
@Service
public class DlyOrderService {

    private static final Short UN_PAY = 0 ;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private DqMode dqMode;

    public void insertOrders(){
        Random random = new Random();
        Order order;

        for (int i = 0; i < 5; i++) {
            long expireTime = random.nextInt(25);
            order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderStatus(UN_PAY);
            orderDao.insertOrder(order,expireTime);
            //订单延时处理
            dqMode.orderDelay(order,expireTime);
        }
    }

    @PostConstruct
    public void initDelayOrder(){
        //todo 查询当前时间大于过期时间的，将状态设置为已过期

        //todo 查询未过期的，将数据重新放入延迟队列中 ps：过期时间需要重新计算
        List<Order> upPay = new ArrayList<>();
        for (Order order :upPay){
            //重新计算过期时间
            long expireTime = order.getExpireTime() - System.currentTimeMillis();
            dqMode.orderDelay(order,expireTime);
        }
    }

}
