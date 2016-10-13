package example;

import org.json.JSONArray;  
import org.json.JSONObject;  
  
public class TestJson02 {  
    public static void main(String[] args) {  
        JSONObject jsonObject = new JSONObject("{'Name':'Tom','age':'11'}");  
        JSONArray jsonArray = new JSONArray("['abc','xyz']");  
          
        //JSONObject内嵌JSONObject  
        JSONObject jsonObject1 = new JSONObject();  
        jsonObject1.put("object1", jsonObject);  
        jsonObject1.put("object2", jsonObject);  
          
        //JSONObject内嵌JSONArray  
        JSONObject jsonObject2 = new JSONObject();  
        jsonObject2.put("JSONArray1", jsonArray);  
        jsonObject2.put("JSONArray2", jsonArray);  
          
        //JSONArray内嵌JSONArray  
        JSONArray jsonArray1 = new JSONArray();  
        jsonArray1.put(jsonArray);  
        jsonArray1.put(jsonArray);  
          
        //JSONArray内嵌JSONObject  
        JSONArray jsonArray2 = new JSONArray();  
        jsonArray2.put(jsonObject);  
        jsonArray2.put(jsonObject);  
          
        System.out.println("JSONObject内嵌JSONObject:" + "\r" + jsonObject1.toString());  
        System.out.println("JSONObject内嵌JSONArray:" + "\r" + jsonObject2.toString());  
        System.out.println("JSONArray内嵌JSONArray:" + "\r" + jsonArray1.toString());  
        System.out.println("JSONArray内嵌JSONObject:" + "\r" + jsonArray2.toString());  
          
    }  
}  