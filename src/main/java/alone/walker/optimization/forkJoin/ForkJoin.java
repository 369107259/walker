package alone.walker.optimization.forkJoin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * @Author: huangYong
 * @Date: 2020/8/22 16:13
 */
public class ForkJoin extends RecursiveTask<List<Object>> {
    private List<Object> params;
    private int start;
    private int end;

    public ForkJoin(List<Object> params, int from, int to) {
        this.params = params;
        this.start = from;
        this.end = to;
    }

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected List<Object> compute() {
        List<Object> result = new CopyOnWriteArrayList<>();
        if (end - start < 3) {
            //todo 执行你需要执行的业务
            List<Object> objects = params.subList(start, end+1);
            List<Object> results = new CopyOnWriteArrayList<>();
            objects.parallelStream().forEach(o -> {
                results.add(o+"结果");
            });
            //业务执行返回的list集合
            result.addAll(results);
        } else {
            int mind = (start + end) / 2;
            ForkJoin left = new ForkJoin(params, start, mind);
            ForkJoin right = new ForkJoin(params, mind + 1, end);
            invokeAll(left,right);
            List<Object> leftRet = left.join();
            List<Object> rightRet = right.join();
            result.addAll(leftRet);
            result.addAll(rightRet);
        }
        return result;
    }
}
