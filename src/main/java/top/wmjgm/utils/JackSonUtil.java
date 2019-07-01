package top.wmjgm.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * <p>
 *     jsonz转换工具
 * </p>
 * @author: Hanne hll941106@163.com
 * @date: 2019-06-30 01:07
 **/
public class JackSonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 实体转json
     * @param entity
     * @return
     */
    public static String entityToJson(Object entity){
        /**
         * 属性值为null时，该属性不做序列化操作
         */
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("数据转换失败，请检查参数是否正确");
        }
        return json;
    }

    /**
     * json转Map
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMap(String json){
        Map<String, Object> map = null;
        try {
            map = objectMapper.readValue(json, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("数据转换失败，请检查参数是否正确");
        }
        return map;
    }

    /**
     * Map转json
     * @param map
     * @return
     */
    public static String mapToJson(Map map){
        String json = null;
        try {
            json = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("数据转换失败，请检查参数是否正确");
        }
        return json;
    }

    /**
     * 实体转二进制数组
     * @param entity
     * @return
     */
    public static byte[] entityToBytes(Object entity){
        byte[] bytes = null;
        try {
            objectMapper.writeValueAsBytes(entity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("数据转换失败，请检查参数是否正确");
        }
        return bytes;
    }

    /**
     * json转实体
     * @param json
     * @param clazz
     * @return
     */
    public static Object jsonToEntity(String json, Class clazz){
        Object object = null;
        try {
            object = objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }
}
