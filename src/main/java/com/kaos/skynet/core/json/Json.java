package com.kaos.skynet.core.json;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

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
     * 序列化
     * 
     * @param src
     * @return
     */
    public void toJson(Object src, Writer writer) {
        gsonHolder.getGson().toJson(src, writer);
    }

    /**
     * 反序列化
     * 
     * @param <T>
     * @param json
     * @param classOfT
     * @return
     */
    public <T> T fromJson(String json, Class<T> classOfT) {
        return gsonHolder.getGson().fromJson(json, classOfT);
    }

    /**
     * 用于反射的反序列化
     * 
     * @param <T>
     * @param reader
     * @param type
     * @return
     */
    public <T> T fromJson(Reader reader, Type type) {
        return gsonHolder.getGson().fromJson(reader, type);
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
