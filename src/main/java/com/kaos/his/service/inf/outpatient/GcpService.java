package com.kaos.his.service.inf.outpatient;

public interface GcpService {
    /**
     * 检查GCP权限
     * 
     * @param deptCode   登陆科室编码
     * @param clinicCode 门诊号
     * @return
     */
    Boolean checkGcpPrivilege(String deptCode, String clinicCode);
}
