package alone.walker.optimization.concurrent.remoteService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: huangYong
 * @Date: 2020/4/9 16:32
 * 模拟远程调用接口
 */
@Service
public class RemoteServiceCall {
    public Map<String, Object> queryOrderInfoByCode(String orderCode) {
        Map<String, Object> result = new HashMap<>(8);
        result.put("orderCode","订单Code");
        return result;
    }
    public List<Map<String, Object>> queryOrderInfoBatch(List<String> orderCodes) {

        List<Map<String,Object>> response = new ArrayList<>();
        for (String orderCode: orderCodes
             ) {
            Map<String, Object> result = new HashMap<>(8);
            result.put("orderCode",orderCode);
            response.add(result);
        }
        return response;
    }
}
