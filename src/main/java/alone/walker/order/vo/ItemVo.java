package alone.walker.order.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Author: huangYong
 * @Date: 2020/5/2 23:02
 * 限时订单封装实体类
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ItemVo<T> implements Delayed {
    /**
     * 到期时间
     */
    private long activeTime;
    /**
     * 业务数据
     */
    private T data;

    public ItemVo(long activeTime, T data) {
        this.activeTime = activeTime * 1000 + System.currentTimeMillis();
        this.data = data;
    }

    /***
     * 返回元素到剩余时间的激活时长
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.activeTime - System.currentTimeMillis(), unit);
    }

    /***
     * 按照剩余时间排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(Delayed o) {
        return Long.compare(getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
    }
}
