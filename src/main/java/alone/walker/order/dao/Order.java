package alone.walker.order.dao;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: huangYong
 * @Date: 2020/5/3 13:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Order {
    private String orderNumber;
    private Short orderStatus;
    private Long expireTime;
}
