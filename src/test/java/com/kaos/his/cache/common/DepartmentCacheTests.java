package com.kaos.his.cache.common;

import com.kaos.his.entity.common.Department;
import com.kaos.inf.ICache;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentCacheTests {
    ICache<Department> departmentCache = DepartmentCache.getInstance();

    @Test
    public void queryDepartment() {
        this.departmentCache.getValue("1000");
    }
}
