package com.kaos.util;

import com.kaos.util.helper.DateHelper;
import com.kaos.util.helper.impl.DateHelperImpl;

public final class DateHelpers {
    public static DateHelper newDateHelper() {
        return new DateHelperImpl();
    }
}
