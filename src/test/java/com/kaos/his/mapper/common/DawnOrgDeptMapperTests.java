package com.kaos.his.mapper.common;

import com.google.common.collect.Lists;
import com.kaos.his.enums.impl.common.DeptOwnEnum;
import com.kaos.his.enums.impl.common.ValidStateEnum;

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
        this.departmentMapper.queryDepartments(DeptOwnEnum.Sourth, Lists.newArrayList(ValidStateEnum.有效));
    }
}
