package com.kaos.his.util;

import com.kaos.his.util.helper.ListHelper;
import com.kaos.his.util.helper.impl.ListHelperImpl;

public final class ListHelpers {
    public static ListHelper newListHelper() {
        return new ListHelperImpl();
    }
}
