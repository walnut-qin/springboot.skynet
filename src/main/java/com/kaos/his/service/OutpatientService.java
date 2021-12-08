package com.kaos.his.service;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.kaos.his.entity.config.VariableList.Variable;
import com.kaos.his.mapper.config.VariableListMapper;
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
    VariableListMapper variableListMapper;

    /**
     * 检查GCP权限
     * 
     * @return
     */
    public Boolean CheckGcpValid(String deptCode, String clinicCode) {
        // 定义查询条件
        final Predicate<Variable> neq = new Predicate<Variable>() {
            @Override
            public boolean apply(Variable input) {
                if (!input.valid || !input.value.equals(deptCode)) {
                    return true;
                }
                return false;
            }
        };

        // 检查科室是否适配
        var lst = this.variableListMapper.QueryVariableList("GcpDept").variables;
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
