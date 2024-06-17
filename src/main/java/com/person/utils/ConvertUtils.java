package com.person.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;

import java.io.IOException;

public class ConvertUtils extends BeanUtils {

    public static final ObjectMapper MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    public static <T> T requestToObject(String data, Class<T> clazz) {
        try {
            JSONObject jsonObj = new JSONObject(data);
            JsonNode root = MAPPER.readTree(data);
            String dataJsonObject = (!"null".equals(root.get("data").toString()) || root.get("data") == null)
                    ? root.get("data").toString()
                    : "{}";
            jsonObj.put("data", dataJsonObject);
            jsonObj.put("dataString", root.get("data").toString());
            return MAPPER.readValue(jsonObj.toString(), clazz);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
