package executor;

import config.Configuration;
import config.MapperStatement;
import reflect.ReflectUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: huangYong
 * @Date: 2020/5/9 11:53
 */
public class DefaultExecutor implements Executor {
    private final Configuration configuration;

    public DefaultExecutor(Configuration configuration) {
        this.configuration = configuration;
    }


    public <E> List<E> query(MapperStatement ms, Object parameter) {
        List<E> ret = new ArrayList<E>();
        try {
            //加载驱动
            Class.forName(configuration.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //建立连接
            conn = DriverManager.getConnection(configuration.getJdbcUrl(), configuration.getJdbcUsername(), configuration.getJdbcPassword());//连接数据库
            //创建PreparedStatement,在MapperStatement中获取sql
            PreparedStatement preparedStatement = conn.prepareStatement(ms.getSql());
            //处理sql语句中的占位符
            parameterize(preparedStatement, parameter);
            //执行获取ResultSet
            ResultSet resultSet = preparedStatement.executeQuery();
            //将结果集通过反射技术，填充到list中
            handlerResultSet(resultSet,ret,ms.getResultType());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    private <E> void handlerResultSet(ResultSet resultSet, List<E> ret, String className) {
        Class<E> eClass = null;
        try {
            //通过反射获取类对象
            eClass = (Class<E>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            while (resultSet.next()){
                try {
                    Object instance = eClass.newInstance();
                    //使用反射工具将数据填充到entity中
                    ReflectUtil.setPropToBeanFromResult(instance,resultSet);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void parameterize(PreparedStatement preparedStatement, Object parameter) throws SQLException {
        if (parameter instanceof Integer) {
            preparedStatement.setInt(1, (Integer) parameter);
        } else if (parameter instanceof Long) {
            preparedStatement.setLong(1, (Long) parameter);
        } else if (parameter instanceof String) {
            preparedStatement.setString(1, (String) parameter);
        }
    }
}
