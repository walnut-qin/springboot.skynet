package com.kaos.his.util.helper.impl;

import java.util.List;

import com.kaos.his.util.helper.ListHelper;

import org.springframework.stereotype.Component;

@Component
public class ListHelperImpl implements ListHelper {
    @Override
    public <T> T getFirst(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public <T> T getLast(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(list.size() - 1);
        }
    }
}
