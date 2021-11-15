package com.kaos.his.entity;

public class User {
    public String code;

    public String name;

    @Override
    public String toString() {
        return String.format("code: %s, name: %s", this.code, this.name);
    }
}
