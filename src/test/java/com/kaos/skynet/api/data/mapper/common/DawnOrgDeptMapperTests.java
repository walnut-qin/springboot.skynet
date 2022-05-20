package com.kaos.skynet.api.data.mapper.common;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.enums.ValidEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DawnOrgDeptMapperTests {
    @Autowired
    DawnOrgDeptMapper deptMapper;

    @Test
    public void queryDepartment() throws Exception {
        this.deptMapper.queryDept(null);
        this.deptMapper.queryDept("1000");
    }

    @Test
    public void queryDepartments() {
        this.deptMapper.queryDepts(DeptOwnEnum.North, null, null);
        this.deptMapper.queryDepts(DeptOwnEnum.All, null, Lists.newArrayList(ValidEnum.VALID));
    }
}
