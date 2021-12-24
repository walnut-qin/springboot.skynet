package com.kaos.util;

import com.google.gson.Gson;
import com.kaos.his.config.GsonConverterConfig;

/**
 * Gson操作助手
 */
public class GsonHelper {
    public static String ToJson(Object src) {
        Gson gson = GsonConverterConfig.GetConfiguredGson();
        return gson.toJson(src);
    }

    public static <T> T FromJson(String json, Class<T> typeOfT) {
        Gson gson = GsonConverterConfig.GetConfiguredGson();
        return gson.fromJson(json, typeOfT);
    }
}
