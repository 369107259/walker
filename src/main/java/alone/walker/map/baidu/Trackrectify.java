package alone.walker.map.baidu;

import java.util.Date;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.NameValuePair;


public class Trackrectify {









    public static void main(String[] args) {







    }





    private static String getReqData() {

        JSONArray reqArray = new JSONArray();

        JSONObject obj = new JSONObject();

        obj.put("latitude", "22.5687645");

        obj.put("longitude", "113.9590789");

        obj.put("coord_type_input", "bd09ll");

        obj.put("loc_time", unixTime());

        reqArray.add(obj);

        return reqArray.toString();

    }



    /**

     * 此处特别注意是unix时间

     */

    public static long unixTime() {

        return new Date().getTime() / 1000;

    }



}