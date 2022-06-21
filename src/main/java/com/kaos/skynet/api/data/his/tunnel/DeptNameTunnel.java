package com.kaos.skynet.api.data.his.tunnel;

import com.kaos.skynet.api.data.his.cache.common.DawnOrgDeptCache;
import com.kaos.skynet.api.data.his.entity.common.DawnOrgDept;
import com.kaos.skynet.core.type.Tunnel;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 科室编码到科室名称的转换器
 */
@Component
public class DeptNameTunnel implements Tunnel<String, String> {
    @Autowired
    DawnOrgDeptCache deptCache;

    @Override
    public String tunneling(String deptCode) {
        // 判空
        if (deptCode == null) {
            return null;
        }

        // 执行逻辑
        DawnOrgDept deptInfo = deptCache.get(deptCode);
        if (deptInfo != null) {
            return deptInfo.getDeptName();
        }
        return deptCode;
    }
}
