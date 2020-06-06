package alone.walker.optimization.concurrent.service;

import alone.walker.optimization.concurrent.remoteService.RemoteServiceCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: huangYong
 * @Date: 2020/4/9 16:31
 */
@Service
public class OrderService {
    @Autowired
    private RemoteServiceCall remoteServiceCall;

    public Map<String,Object> queryOrderInfo(String orderCode){
        //远程调用接口
        return remoteServiceCall.queryOrderInfoByCode(orderCode);
    }
}
