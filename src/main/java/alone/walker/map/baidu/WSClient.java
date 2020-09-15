package alone.walker.map.baidu;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * @Author: huangYong
 * @Date: 2020/9/1 15:46
 */
public class WSClient {

    /**
     * 接收http post协议的信息
     * @param url 请求地址
     * @param data 表单参数
     * @param encoding 编码
     * @return 服务端返回信息
     * @throws Exception
     */
    public static String httpPost(String url, NameValuePair[] data, String encoding){
        PostMethod hm = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try{
            MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
            HttpClient hc = new HttpClient(connectionManager);
            hc.getParams().setHttpElementCharset(encoding);
            hm = new PostMethod(url);
            hm.setRequestBody(data);
            hm.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            hc.executeMethod(hm);
            if(hm.getStatusCode()==200){
                is = hm.getResponseBodyAsStream();
                isr = new InputStreamReader(is,encoding);
                br = new BufferedReader(isr);
                String line="";
                StringBuffer sb = new StringBuffer();
                while((line=br.readLine())!=null){
                    sb.append(line);
                }
                return sb.toString();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if(null!=br){
                    br.close();
                }
                if(null!=isr){
                    isr.close();
                }
                if(null!=is){
                    is.close();
                }
                if(null!=hm){
                    hm.releaseConnection();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

}
