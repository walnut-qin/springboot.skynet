package com.kaos.his.common;

import com.kaos.his.enums.ValidStateEnum;
import com.kaos.his.mapper.common.DepartmentMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentMapperTests {
    @Autowired
    DepartmentMapper departmentMapper;

    @Test
    public void queryDepartment() throws Exception {
        this.departmentMapper.queryDepartment("1000", null);
        this.departmentMapper.queryDepartment("1000", ValidStateEnum.作废);
        this.departmentMapper.queryDepartment("1000", ValidStateEnum.无效);
        this.departmentMapper.queryDepartment("1000", ValidStateEnum.有效);
    }
}
