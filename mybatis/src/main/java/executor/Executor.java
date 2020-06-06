package executor;

import config.MapperStatement;

import java.util.List;

/**
 * @Author: huangYong
 * @Date: 2020/5/9 11:50
 */
public interface Executor {
    <E> List<E> query(MapperStatement ms, Object parameter);

}
