package alone.walker.event;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: huangYong
 * @Date: 2020/7/1 18:22
 * 时间驱动编程
 */
@Component
public class ApplicationEvent {

    @Resource
    private ApplicationContext context;

    public void event(Map map){
        // TODO: 2020/7/1  业务操作

        //发布事件
        context.publishEvent(map);
        System.out.println("事件发送完成");
    }
}
