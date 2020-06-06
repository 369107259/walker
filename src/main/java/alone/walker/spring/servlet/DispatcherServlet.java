package alone.walker.spring.servlet;


import alone.walker.spring.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: huangYong
 * @Date: 2020/4/15 14:50
 * spring mvc源码分析
 * 需要配置web.xml得DispatcherServlet
 */
public class DispatcherServlet extends HttpServlet {
    /**
     * 用于保存类全路径得集合
     */
    private List<String> classNames = new ArrayList<>();
    /**
     * 存放bean的容器
     */
    private Map<String, Object> beans = new HashMap<>();
    /***
     * 存放路径--方法映射容器
     */
    private Map<String, Object> handlerMapping = new HashMap<>();

    /***
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取url访问路径
        String requestURI = req.getRequestURI();
        //获取项目名路径
        String contextPath = req.getContextPath();
        String handlerMapping = requestURI.replace(contextPath, "");
        Object instance = beans.get("/" + handlerMapping.split("/")[1]);
        Method method = (Method) this.handlerMapping.get(handlerMapping);
        try {
            method.invoke(instance,hand(req,resp,method));
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /***
     * tomcat 启动 spring 初始化需要做得事情
     * 1扫描类路径 --
     * 2实例化对象（反射）
     * 3autowired --依赖处理
     * 4路径映射 -- urlHandlerMapping
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        scanBasePackage("confused.future");
        doInstance();
        doAutowired();
        urlHandlerMapping();

    }

    /***
     * 扫描类路径
     * @param basePackage
     */
    private void scanBasePackage(String basePackage) {
        //获取扫描类路径
        URL resource = this.getClass().getClassLoader().getResource("/" + basePackage.replaceAll("\\.", "/"));
        //获取路径下所有文件和文件夹
        String fileStr = resource.getFile();
        File file = new File(fileStr);
        String[] fileList = file.list();
        for (String path : fileList
        ) {
            File filePath = new File(path);
            //判断是否为文件夹 是-递归获取文件夹下路径
            if (filePath.isDirectory()) {
                scanBasePackage(basePackage + "." + path);
            } else {
                classNames.add(basePackage + "." + filePath.getName());
            }
        }
    }

    /***
     * 实例化对象
     */
    private void doInstance() {
        for (String className : classNames
        ) {
            //文件全路径
            String fullPath = className.replace(".class", "");
            try {
                Class<?> aClass = Class.forName(fullPath);
                //实例化
                Object instance = aClass.newInstance();
                //使用EnjoyController注解得控制层类
                if (aClass.isAnnotationPresent(EnjoyController.class)) {
                    //将注解的值作为实例化对象的key，spring的做法是将类名首字母小写作为key
                    EnjoyRequestMapping annotation = aClass.getAnnotation(EnjoyRequestMapping.class);
                    String controllerKey = annotation.value();
                    beans.put(controllerKey, instance);

                    //使用EnjoyService注解得服务层类
                } else if (aClass.isAnnotationPresent(EnjoyService.class)) {
                    EnjoyService annotation = aClass.getAnnotation(EnjoyService.class);
                    String serviceKey = annotation.value();
                    beans.put(serviceKey, instance);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }

    /***
     * 依赖处理
     */
    private void doAutowired() {
        //将所有使用@EnjoyAutowired的注解的属性都依赖注入
        beans.forEach((key, value) -> {
            Class<?> clazz = value.getClass();
            //获取类中所有声明的字段  clazz.getFields()只能获取公有的字段
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields
            ) {
                if (field.isAnnotationPresent(EnjoyAutowired.class)) {
                    EnjoyAutowired autowired = field.getAnnotation(EnjoyAutowired.class);
                    String auto = autowired.value();
                    Object target = beans.get(auto);
                    //设置私有属性可以设值
                    field.setAccessible(true);
                    try {
                        field.set(value, target);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /***
     * 路径映射
     */
    private void urlHandlerMapping() {
        beans.forEach((key, value) -> {
            //根据对象获取class
            Class<?> clazz = value.getClass();
            //针对控制层url路径映射
            if (clazz.isAnnotationPresent(EnjoyController.class)) {
                //通过类上和方法上的@EnjoyRequestMapping注解的value值 和类中的方法一一对应，放入对应容器中
                EnjoyRequestMapping requestMapping = clazz.getAnnotation(EnjoyRequestMapping.class);
                String classPath = requestMapping.value();
                Method[] methods = clazz.getMethods();
                for (Method method : methods
                ) {
                    if (method.isAnnotationPresent(EnjoyRequestMapping.class)) {
                        EnjoyRequestMapping methodRequestMapping = method.getAnnotation(EnjoyRequestMapping.class);
                        String methodPath = methodRequestMapping.value();
                        handlerMapping.put(classPath + methodPath, method);
                    }
                }
            }
        });
    }

    public static Object[] hand(HttpServletRequest request, HttpServletResponse response, Method method) {
        //拿到当前执行方法有哪些参数
        Class<?>[] parameterTypes = method.getParameterTypes();
        //根据参数的个数，new一个参数的数组，将方法中所有参数赋值到数组中
        Object[] args = new Object[parameterTypes.length];
        //
        int args_i = 0;
        int index = 0;
        for (Class<?> paramClass : parameterTypes
        ) {
            if (ServletRequest.class.isAssignableFrom(paramClass)) {
                args[args_i++] = request;
            }
            if (ServletResponse.class.isAssignableFrom(paramClass)) {
                args[args_i++] = response;
            }
            Annotation[] parameterAnnotations = method.getParameterAnnotations()[index];
            if (parameterAnnotations.length > 0) {
                for (Annotation param : parameterAnnotations
                ) {
                    if (EnjoyRequestParam.class.isAssignableFrom(param.getClass())) {
                        EnjoyRequestParam enjoyRequestParam = (EnjoyRequestParam) param;
                        args[args_i++] = request.getParameter(enjoyRequestParam.value());
                    }
                }
            }
            index++;
        }
        return args;
    }
}
