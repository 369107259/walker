package alone.walker.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: huangYong
 * @Date: 2020/7/1 18:38
 */
@SpringBootTest
class ApplicationEventTest {

    @Autowired
    private ApplicationEvent applicationEvent;
    @Test
    void event() {
        HashMap hashMap = new HashMap();
        hashMap.put("1","1");
        applicationEvent.event(hashMap);
    }
}