package com.kaos.his.mapper.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentMapperTests {
    @Autowired
    DawnOrgDeptMapper departmentMapper;

    @Test
    public void queryDepartment() throws Exception {
        this.departmentMapper.queryDepartment(null);
        this.departmentMapper.queryDepartment("1000");
    }
}
