package com.kaos.skynet.api.data.his.mapper.common;

import com.kaos.skynet.api.data.his.enums.DeptOwnEnum;

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
        deptMapper.queryDepts(DawnOrgDeptMapper.Key.builder().deptOwn(DeptOwnEnum.North).build());
    }
}
