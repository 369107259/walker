package alone.walker.optimization.concurrent.controller;

import alone.walker.optimization.concurrent.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: huangYong
 * @Date: 2020/4/9 16:29
 * 并发远程调用三方订单接口模拟实例
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    public Map<String,Object> queryOrderInfo(@RequestParam("orderCode")String orderCode){
        return  orderService.queryOrderInfo(orderCode);
    }
}
