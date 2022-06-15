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
     * @param src 数据源
     * @return
     */
    public String toJson(Object src) {
        return gsonHolder.getGson().toJson(src);
    }

    /**
     * 序列化
     * 
     * @param src    数据源
     * @param writer 写入器
     */
    public void toJson(Object src, Writer writer) {
        gsonHolder.getGson().toJson(src, writer);
    }

    /**
     * 反序列化
     * 
     * @param <T>  反序列化目标类型
     * @param json 数据源读取器
     * @param type 目标类型
     * @return
     */
    public <T> T fromJson(String json, Type type) {
        return gsonHolder.getGson().fromJson(json, type);
    }

    /**
     * 用于反射的反序列化
     * 
     * @param <T>    反序列化目标类型
     * @param reader 数据源读取器
     * @param type   目标类型
     * @return
     */
    public <T> T fromJson(Reader reader, Type type) {
        return gsonHolder.getGson().fromJson(reader, type);
    }

    /**
     * 深拷贝
     * 
     * @param <T>  目标类型
     * @param src  拷贝源
     * @param type 目标类型
     * @return
     */
    public <T> T clone(T src, Type type) {
        // 序列化
        String str = this.toJson(src);

        // 反序列化
        return this.fromJson(str, type);
    }
}
