package util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertObjectToJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Hoặc xử lý lỗi theo cách khác
        }
    }
}
