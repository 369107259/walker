package session;

import config.Configuration;
import config.MapperStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * @Author: huangYong
 * @Date: 2020/5/8 18:10
 */
//完成配置信息的解析和加载
//生成sqlSession
public class SqlSessionFactory {
    private final Configuration configuration = new Configuration();

    public SqlSessionFactory() {
        loadDbInfo();
        loadMappersInfo();
    }

    //mapper.xml 存放位置
    public static final String MAPPER_CONFIG_LOCATION = "mappers";
    //数据库连接信息存放位置
    public static final String DB_CONFIG_FILE = "db.properties";


    //加载数据库配置信息
    private void loadDbInfo() {
        InputStream inputStream = SqlSessionFactory.class.getClassLoader().getResourceAsStream(DB_CONFIG_FILE);
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将数据库配置信息写入Configuration对象中
        configuration.setJdbcDriver(properties.getProperty("jdbc.driver"));
        configuration.setJdbcUrl(properties.getProperty("jdbc.url"));
        configuration.setJdbcUsername(properties.getProperty("jdbc.username"));
        configuration.setJdbcPassword(properties.getProperty("jdbc.password"));
    }

    //加载指定文件夹下所有mapper.xml
    private void loadMappersInfo() {
        URL resources = null;
        try {
            resources = SqlSessionFactory.class.getClassLoader().getResources(MAPPER_CONFIG_LOCATION).nextElement();
            File file = new File(resources.getFile());
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File file1 : files) {
                    loadMapperInfo(file1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //加载指定的mapper.xml文件
    private void loadMapperInfo(File file1) {
        //创建saxReader对象
        SAXReader saxReader = new SAXReader();
        //通过reader方法读取文件，生成document对象
        try {
            Document document = saxReader.read(file1);
            Element rootElement = document.getRootElement();
            //命名空间
            String namespace = rootElement.attribute("namespace").getData().toString();
            //子节点
            List<Element> select = rootElement.elements("select");
            for (Element element : select) {
                MapperStatement mapperStatement = new MapperStatement();
                String id = element.attribute("id").getData().toString();
                String resultType = element.attribute("resultType").getData().toString();
                String sql = element.getData().toString();
                String sourceId = namespace + "." + id;

                mapperStatement.setSourceId(sourceId);
                mapperStatement.setNameSpace(namespace);
                mapperStatement.setResultType(resultType);
                mapperStatement.setSql(sql);
                configuration.getMapperStatements().put(sourceId,mapperStatement);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    //生产sqlSession
    public SqlSession openSession(){
        return new DefaultSqlSession(configuration);
    }
}
