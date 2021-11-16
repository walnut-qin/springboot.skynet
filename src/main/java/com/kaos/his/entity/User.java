package com.kaos.his.entity;

import com.kaos.his.enums.SexEnum;

public class User {
    public String code;

    public String name;

    public SexEnum sex;

    @Override
    public String toString() {
        return String.format("code: %s, name: %s, sex: %s", this.code, this.name, this.sex.getDescription());
    }
}
