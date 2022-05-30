package com.kaos.skynet.core.json;

import com.kaos.skynet.core.json.gson.GsonHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Json {
    /**
     * Gson类型的保持器
     */
    @Autowired
    GsonHolder gsonHolder;

    /**
     * 序列化
     * 
     * @param src
     * @return
     */
    public String toJson(Object src) {
        return gsonHolder.getGson().toJson(src);
    }

    /**
     * 反序列化
     * 
     * @param <T>
     * @param json
     * @param classOfT
     * @return
     */
    public <T> T fromJson(String json, Class<T> classOfT) {// 反射出classOfV
        return gsonHolder.getGson().fromJson(json, classOfT);
    }

    /**
     * 深拷贝
     * 
     * @param <T>
     * @param src
     * @param classOfT
     * @return
     */
    public <T> T clone(T src, Class<T> classOfT) {
        // 序列化
        String str = this.toJson(src);

        // 反序列化
        return this.fromJson(str, classOfT);
    }
}
