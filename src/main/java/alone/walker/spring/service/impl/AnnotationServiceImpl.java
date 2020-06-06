package alone.walker.spring.service.impl;


import alone.walker.spring.annotation.EnjoyService;
import alone.walker.spring.service.IAnnotationService;

/**
 * @Author: huangYong
 * @Date: 2020/4/15 14:35
 */
@EnjoyService
public class AnnotationServiceImpl implements IAnnotationService {

    /***
     * 注解测试service实现
     * @param name
     * @param age
     * @return
     */
    @Override
    public String query(String name, String age) {
        return name+"==="+age;
    }
}
