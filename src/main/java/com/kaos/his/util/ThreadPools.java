package com.kaos.his.util;

import com.kaos.his.util.helper.ThreadPool;
import com.kaos.his.util.helper.impl.ThreadPoolImpl;

public final class ThreadPools {
    public static ThreadPool newThreadPool(int corePoolSize) {
        return new ThreadPoolImpl(corePoolSize);
    }
}
