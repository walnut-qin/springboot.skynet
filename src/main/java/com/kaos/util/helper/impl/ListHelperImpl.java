package com.kaos.util.helper.impl;

import java.util.List;

import com.kaos.util.helper.ListHelper;

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

    @Override
    public String join(String separator, List<String> list) {
        String oStr = null;
        for (String iStr : list) {
            if (iStr == null || iStr.equals("")) {
                continue;
            } else if (oStr == null) {
                oStr = iStr;
            } else {
                oStr += String.format("%s%s", separator, iStr);
            }
        }
        return oStr;
    }
}
