package com.kaos.his.service;

import com.kaos.his.mapper.config.DawnCodeTypeMapper;
import com.kaos.his.mapper.organization.DepartmentMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugHedgingService {
    /**
     * 常量查询接口
     */
    @Autowired
    DawnCodeTypeMapper dawnCodeTypeMapper;

    /**
     * 科室信息接口
     */
    @Autowired
    DepartmentMapper deptMapper;

    /**
     * 根据科室编码查询退药类型
     * 
     * @param deptCode
     * @return
     */
    public String QueryHedgingType(String deptCode) {
        // 根据科室编码查询配置的常量
        var cfg = this.dawnCodeTypeMapper.QueryValidCodeType(deptCode);
        if (cfg == null || cfg.name == null) {
            return "有药退药";
        }


        // 查询科室名称
        var dept = this.deptMapper.QueryDepartment(deptCode);
        if (dept == null) {
            return cfg.name;
        }

        // 构造过滤pattern
        String pattern = String.format("(%s)|\\+", dept.name);

        // 过滤科室信息
        return cfg.name.replaceAll(pattern, "");
    }
}
