package com.kaos.skynet.util;

import com.kaos.skynet.util.helper.ThreadPool;
import com.kaos.skynet.util.helper.impl.ThreadPoolImpl;

public final class ThreadPools {
    public static ThreadPool newThreadPool(int corePoolSize) {
        return new ThreadPoolImpl(corePoolSize);
    }
}
