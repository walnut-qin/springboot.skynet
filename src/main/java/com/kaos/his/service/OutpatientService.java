package com.kaos.his.service;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.kaos.his.entity.config.ParamList.Param;
import com.kaos.his.mapper.config.ParamListMapper;
import com.kaos.his.mapper.personnel.OutpatientMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutpatientService {
    /**
     * 门诊患者实体接口
     */
    @Autowired
    OutpatientMapper outpatientMapper;

    /**
     * 参数列表接口
     */
    @Autowired
    ParamListMapper paramListMapper;

    /**
     * 检查GCP权限
     * 
     * @return
     */
    public Boolean CheckGcpValid(String deptCode, String clinicCode) {
        // 定义查询条件
        final Predicate<Param> neq = new Predicate<Param>() {
            @Override
            public boolean apply(Param input) {
                if (!input.value.equals(deptCode)) {
                    return true;
                }
                return false;
            }
        };

        // 检查科室是否适配
        var lst = this.paramListMapper.QueryParamList("GcpDept", true).params;
        if (Iterators.all(lst.iterator(), neq)) {
            return false;
        }

        // 获取门诊实体
        var outpatient = this.outpatientMapper.QueryOutpatient(clinicCode);
        if (outpatient == null) {
            return false;
        }

        // 检查患者是否为GCP患者
        return outpatient.gcpOutPatient;
    }
}
