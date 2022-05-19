package com.kaos.skynet.api.mapper.common;

import com.kaos.skynet.enums.common.DeptOwnEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DawnOrgDeptMapperTests {
    @Autowired
    DawnOrgDeptMapper departmentMapper;

    @Test
    public void queryDepartment() throws Exception {
        this.departmentMapper.queryDepartment(null);
        this.departmentMapper.queryDepartment("1000");
    }

    @Test
    public void queryDepartments() {
        this.departmentMapper.queryDepartments(DeptOwnEnum.North, null, null);
        this.departmentMapper.queryDepartments(DeptOwnEnum.All, null, null);
    }
}
