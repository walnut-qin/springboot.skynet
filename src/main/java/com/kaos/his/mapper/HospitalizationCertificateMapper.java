package com.kaos.his.mapper;

import com.kaos.his.entity.credential.HospitalizationCertificate;

import org.springframework.stereotype.Repository;

@Repository
public interface HospitalizationCertificateMapper {
    /**
     * 获取住院证
     * 
     * @param cardNo
     * @param happenNo
     * @return
     */
    HospitalizationCertificate GetHospitalizationCertificate(String cardNo, Integer happenNo);
}
