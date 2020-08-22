package alone.walker.optimization.forkJoin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * @Author: huangYong
 * @Date: 2020/8/22 16:50
 */
public class ForkJoinPoll {
    public static void main(String[] args) {
        List<Object> param =  Arrays.asList(1,2,4,5,6,7,8,9,0);//你的list
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoin forkJoin = new ForkJoin(param,0,param.size()-1);
        List<Object> invoke = forkJoinPool.invoke(forkJoin);
        //invoke 为获取的最终结果集
        System.out.println(invoke);
    }
}
