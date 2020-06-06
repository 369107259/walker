package reflect;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: huangYong
 * @Date: 2020/5/11 22:38
 */
public class ReflectUtil {
    /***
     *
     * @param bean 目标对象
     * @param propName 对象的属性名
     * @param value 值
     */
    public static void setPropToBean(Object bean, String propName, Object value) {
        //获取指定对象属性
        Field declaredField = null;
        try {
            declaredField = bean.getClass().getDeclaredField(propName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        //设置可通过反射进行访问
        declaredField.setAccessible(true);
        //设值
        try {
            declaredField.set(bean, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /***
     *
     * @param entity 返回的实体类
     * @param resultSet 查询的结果集
     */
    public static void setPropToBeanFromResult(Object entity, ResultSet resultSet) throws SQLException {
        Field[] declaredFields = entity.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.getType().getSimpleName().equals("String")) {
                setPropToBean(entity, field.getName(), resultSet.getString(field.getName()));
            } else if (field.getType().getSimpleName().equals("Integer")) {
                setPropToBean(entity, field.getName(), resultSet.getInt(field.getName()));
            } else if (field.getType().getSimpleName().equals("Long")) {
                setPropToBean(entity, field.getName(), resultSet.getLong(field.getName()));
            }
        }
    }
}
