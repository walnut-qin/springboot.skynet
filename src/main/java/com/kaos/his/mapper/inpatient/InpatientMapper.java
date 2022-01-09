package com.kaos.his.mapper.inpatient;

import java.util.List;

import com.kaos.his.entity.inpatient.Inpatient;

public interface InpatientMapper {
    /**
     * 主键查询
     * 
     * @param inpatientNo
     * @return
     */
    Inpatient queryInpatient(String inpatientNo);

    /**
     * 查询住院列表
     * 
     * @param cardNo   就诊卡号
     * @param happenNo 住院证编号
     * @return
     */
    List<Inpatient> queryInpatients(String cardNo, Integer happenNo);
}
