package alone.walker.order.service;

import alone.walker.order.dao.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: huangYong
 * @Date: 2020/5/3 13:28
 */
@Component
@Slf4j
public class DlyOrderProcessor {

    /***
     * 检查订单状态 如果为未支付，则修改为已过期
     * @param data
     */
    public void checkDelayOrder(Order data) {
        log.info("检查完毕");
    }
}
