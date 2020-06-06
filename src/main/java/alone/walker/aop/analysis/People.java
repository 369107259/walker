package alone.walker.aop.analysis;

/**
 * @Author: huangYong
 * @Date: 2020/3/12 17:57
 */
public class People implements Girl {
    @Override
    public void data() {
        //这种方式相当于通过this调用该方法，外部使用动态代理会使动态代理失效
        watchMovie();
    }

    @Override
    public void watchMovie() {

    }
}
