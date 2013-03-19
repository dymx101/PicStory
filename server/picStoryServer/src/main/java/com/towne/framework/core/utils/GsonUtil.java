package com.towne.framework.core.utils;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

public class GsonUtil {
    private static Gson gson = null;  
    
    static {  
        if (gson == null) {  
            gson = new Gson();  
        }  
    }  
  
    private GsonUtil() {  
      
    }  
  
    /** 
     * 将对象转换成json格式 
     *  
     * @param ts 
     * @return 
     */  
    public static String objectToJson(Object ts) {  
        String jsonStr = null;  
        if (gson != null) {  
            jsonStr = gson.toJson(ts);  
        }  
        return jsonStr;  
    }  
 
  
    /** 
     * 将json格式转换成list对象 
     *  
     * @param jsonStr 
     * @return 
     */  
    public static List<?> jsonToList(String jsonStr) {  
        List<?> objList = null;  
        if (gson != null) {  
            java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<?>>() {  
            }.getType();  
            objList = gson.fromJson(jsonStr, type);  
        }  
        return objList;  
    }  
      
    /** 
     * 将json格式转换成list对象，并准确指定类型 
     * @param jsonStr 
     * @param type 
     * @return 
     */  
    public static List<?> jsonToList(String jsonStr, java.lang.reflect.Type type) {  
        List<?> objList = null;  
        if (gson != null) {  
            objList = gson.fromJson(jsonStr, type);  
        }  
        return objList;  
    }  
  
    /** 
     * 将json格式转换成map对象 
     *  
     * @param jsonStr 
     * @return 
     */  
    public static Map<?, ?> jsonToMap(String jsonStr) {  
        Map<?, ?> objMap = null;  
        if (gson != null) {  
            java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<?, ?>>() {  
            }.getType();  
            objMap = gson.fromJson(jsonStr, type);  
        }  
        return objMap;  
    }  
  
    /** 
     * 将json转换成Model对象 
     *  
     * @param jsonStr 
     * @return 
     */  
    public static Object jsonToModel(String jsonStr, Class<?> cl) {  
        Object obj = null;  
        if (gson != null) {  
            obj = gson.fromJson(jsonStr, cl);  
        }  
        return obj;  
    }  
  
    /** 
     * 根据 
     *  
     * @param jsonStr 
     * @param key 
     * @return 
     */  
    public static Object getJsonValue(String jsonStr, String key) {  
        Object rulsObj = null;  
        Map<?, ?> rulsMap = jsonToMap(jsonStr);  
        if (rulsMap != null && rulsMap.size() > 0) {  
            rulsObj = rulsMap.get(key);  
        }  
        return rulsObj;  
    }  
    
    /** 
     * 根据 
     *  
     * @param jsonStr 
     * @param key 
     * @param type
     * @return 
     */  
    public static Object getJsonValue(String jsonStr, String key ,Class<?> cl) {  
        Object rulsObj = null;  
        Map<?, ?> rulsMap = jsonToMap(jsonStr);  
        if (rulsMap != null && rulsMap.size() > 0) {  
            rulsObj = rulsMap.get(key);  
            rulsObj = gson.fromJson(gson.toJson(rulsObj), cl);  
        }  
        return rulsObj;  
    }
 
}
