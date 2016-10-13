package example;

import org.json.JSONObject;  
/*
 * 解析数据
 */
public class TestJson03 {  
    public static void main(String[] args) {  
        //解析字符串  
        JSONObject jsonObject1 = new JSONObject();  
        jsonObject1.put("Name", "Tom");  
        jsonObject1.put("age", "11");  
        System.out.println("解析字符串:" + "\r" + jsonObject1.getString("Name"));  
          
        //解析对象  
        JSONObject jsonObject = new JSONObject("{'Name':'Tom','age':'11'}");  
        JSONObject jsonObject2 = new JSONObject();  
        jsonObject2.put("jsonObject", jsonObject);  
          
        System.out.println("解析对象:");  
        System.out.println(jsonObject2.getJSONObject("jsonObject").toString());  
    }  
  
}  