package com.ncjavaedu.ediary.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class JSONUtils {

    private static final Logger logger = LoggerFactory.getLogger(JSONUtils.class);
    private static final JSONUtils instance = new JSONUtils();

    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setDateFormat("MM.dd.yyyy")
            .create();

    private JSONUtils() {
    }

    public static JSONUtils getInstance() {
        return instance;
    }

    public <T> T readJsonFromResource(String resource, Type type, Class callerClass) {
        try {
            InputStreamReader streamReader = new InputStreamReader(callerClass.getResourceAsStream(resource));
            JsonReader reader = new JsonReader(streamReader);
            return gson.fromJson(reader, type);
        } catch (JsonParseException e){
            logger.error(e.getMessage());
        }
        return null;
    }
}
