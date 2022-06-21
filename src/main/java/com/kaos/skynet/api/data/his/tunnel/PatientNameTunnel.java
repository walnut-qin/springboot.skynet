package com.kaos.skynet.api.data.his.tunnel;

import com.kaos.skynet.api.data.his.cache.DataCache;
import com.kaos.skynet.api.data.his.entity.common.ComPatientInfo;
import com.kaos.skynet.core.type.Tunnel;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class PatientNameTunnel implements Tunnel<String, String> {
    @Autowired
    DataCache dataCache;

    @Override
    public String tunneling(String cardNo) {
        // 判空
        if (cardNo == null) {
            return null;
        }

        // 执行逻辑
        ComPatientInfo patient = dataCache.getPatientInfoCache().get(cardNo);
        if (patient != null) {
            return patient.getName();
        }
        return cardNo;
    }
}
