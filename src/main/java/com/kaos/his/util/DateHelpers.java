package com.kaos.his.util;

import com.kaos.his.util.helper.DateHelper;
import com.kaos.his.util.helper.impl.DateHelperImpl;

public final class DateHelpers {
    public static DateHelper newDateHelper() {
        return new DateHelperImpl();
    }
}
