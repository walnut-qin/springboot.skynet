package com.kaos.his.cache.common;

import com.kaos.his.entity.common.Department;
import com.kaos.inf.ICache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentCacheTests {
    @Autowired
    ICache<Department> departmentCache;

    @Test
    public void queryDepartment() {
        this.departmentCache.getValue("1000");
    }
}
