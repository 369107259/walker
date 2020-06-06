package alone.walker.jvm;

/**
 * @Author: huangYong
 * @Date: 2019/12/17 14:18
 */
public class MyStack {
    private Object[] elements;
    private int size=0;
    private static final int CAP=16;

    public MyStack() {
        this.elements = new Object[CAP];
    }

    public void push(Object e){
        elements[size++] = e;
    }

    public Object pop(){
        Object o = elements[--size];
        //使GC可以工作，防止内存泄漏  可以对比ArrayList的remove方法
        elements[--size] = null;
        return o;
    }

}
