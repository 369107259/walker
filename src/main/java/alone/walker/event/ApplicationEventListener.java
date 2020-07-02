package alone.walker.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: huangYong
 * @Date: 2020/7/1 18:25
 */
@Component
public class ApplicationEventListener {
    public void doSomething(){
        // TODO: 2020/7/1   处理相应的事情
        System.out.println("接受事件处理完成");
    }

    @EventListener
    @Async
    public void eventConsumer(Map map){
        doSomething();
    }
}
