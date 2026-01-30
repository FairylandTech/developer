/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Lionel Johnson
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2025-11-24 23:20:09 UTC+08:00
 ****************************************************/
package host.fairy;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lionel Johnson
 * @version 1.0
 */
public class Test {
    public static void main(String[] args) throws Exception {
        
        Map<String, List<Map<String, String>>> plainDict = new HashMap<>();
        
        // 构建 key1 的列表
        List<Map<String, String>> list1 = new ArrayList<>();
        Map<String, String> item1_1 = new HashMap<>();
        item1_1.put("id", "1");
        item1_1.put("name", "1");
        item1_1.put("value", "10.0");
        list1.add(item1_1);
        
        Map<String, String> item1_2 = new HashMap<>();
        item1_2.put("id", "2");
        item1_2.put("name", "2");
        item1_2.put("value", "20.0");
        list1.add(item1_2);
        
        plainDict.put("key1", list1);
        
        // 构建 key2 的列表
        List<Map<String, String>> list2 = new ArrayList<>();
        Map<String, String> item2_1 = new HashMap<>();
        item2_1.put("id", "3");
        item2_1.put("name", "3");
        item2_1.put("value", "30.0");
        list2.add(item2_1);
        
        Map<String, String> item2_2 = new HashMap<>();
        item2_2.put("id", "4");
        item2_2.put("name", "4");
        item2_2.put("value", "40.0");
        list2.add(item2_2);
        
        plainDict.put("key2", list2);
        
        System.out.println(plainDict);
        
        String jsonString = JSON.toJSONString(plainDict);
        
        System.out.println(jsonString);
        System.out.println("==");
        
        String string = new ObjectMapper().writeValueAsString(plainDict);
        
        System.out.println(string);
    }
}
