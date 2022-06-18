package com.kaos.skynet.api.data.his.router;

import com.kaos.skynet.api.data.his.cache.DataCache;
import com.kaos.skynet.api.data.his.entity.common.DawnOrgDept;
import com.kaos.skynet.core.type.Router;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 科室编码到科室名称的转换器
 */
@Component
public class DeptNameRouter implements Router<String, String> {
    @Autowired
    DataCache dataCache;

    @Override
    public String route(String deptCode) {
        // 判空
        if (deptCode == null) {
            return null;
        }

        // 执行逻辑
        DawnOrgDept deptInfo = dataCache.getDeptCache().get(deptCode);
        if (deptInfo != null) {
            return deptInfo.getDeptName();
        }
        return deptCode;
    }
}
