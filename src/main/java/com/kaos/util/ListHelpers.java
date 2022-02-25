package com.kaos.util;

import com.kaos.util.helper.ListHelper;
import com.kaos.util.helper.impl.ListHelperImpl;

public final class ListHelpers {
    public static ListHelper newListHelper() {
        return new ListHelperImpl();
    }
}
