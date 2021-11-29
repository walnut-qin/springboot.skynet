package com.kaos.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.his.enums.DeptOwnEnum;
import com.kaos.his.enums.DrugItemGradeEnum;
import com.kaos.his.enums.DrugShiftTypeEnum;
import com.kaos.his.enums.DrugValidStateEnum;
import com.kaos.his.enums.EscortActionEnum;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.enums.InpatientStateEnum;
import com.kaos.his.enums.OutpatientStateEnum;
import com.kaos.his.enums.PreinCardStateEnum;
import com.kaos.his.enums.SexEnum;
import com.kaos.his.enums.util.GsonEnumTypeAdapter;

public class GsonHelper {
    /**
     * 获取通用Gson对象；注册各种枚举适配器
     * 
     * @return
     */
    private static Gson GetUniversalGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(DeptOwnEnum.class, new GsonEnumTypeAdapter<>(DeptOwnEnum.class))
                .registerTypeAdapter(DrugItemGradeEnum.class, new GsonEnumTypeAdapter<>(DrugItemGradeEnum.class))
                .registerTypeAdapter(DrugShiftTypeEnum.class, new GsonEnumTypeAdapter<>(DrugShiftTypeEnum.class))
                .registerTypeAdapter(DrugValidStateEnum.class, new GsonEnumTypeAdapter<>(DrugValidStateEnum.class))
                .registerTypeAdapter(EscortActionEnum.class, new GsonEnumTypeAdapter<>(EscortActionEnum.class))
                .registerTypeAdapter(EscortStateEnum.class, new GsonEnumTypeAdapter<>(EscortStateEnum.class))
                .registerTypeAdapter(InpatientStateEnum.class, new GsonEnumTypeAdapter<>(InpatientStateEnum.class))
                .registerTypeAdapter(OutpatientStateEnum.class, new GsonEnumTypeAdapter<>(OutpatientStateEnum.class))
                .registerTypeAdapter(PreinCardStateEnum.class, new GsonEnumTypeAdapter<>(PreinCardStateEnum.class))
                .registerTypeAdapter(SexEnum.class, new GsonEnumTypeAdapter<>(SexEnum.class)).create();
    }

    public static String ToJson(Object src) {
        Gson gson = GsonHelper.GetUniversalGson();
        return gson.toJson(src);
    }

    public static <T> T FromJson(String json, Class<T> typeOfT) {
        Gson gson = GsonHelper.GetUniversalGson();
        return gson.fromJson(json, typeOfT);
    }
}
