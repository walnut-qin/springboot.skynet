package com.kaos.skynet.api.data.his.mapper.common;

import com.kaos.skynet.api.data.his.entity.common.PatientEscortRelationship;

public interface PatientEscortRelationshipMapper {
    /**
     * 查询关系
     * 
     * @param patientCardNo
     * @param escortCardNo
     * @return
     */
    PatientEscortRelationship queryRelationship(String patientCardNo, String escortCardNo);
}
